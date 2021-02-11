package com.ironhack.midtermproject.service.impl;

import com.ironhack.midtermproject.Money;
import com.ironhack.midtermproject.enums.AccountType;
import com.ironhack.midtermproject.enums.Status;
import com.ironhack.midtermproject.model.accounts.*;
import com.ironhack.midtermproject.model.users.AccountHolder;
import com.ironhack.midtermproject.model.users.User;
import com.ironhack.midtermproject.repository.accounts.*;
import com.ironhack.midtermproject.repository.users.AccountHolderRepository;
import com.ironhack.midtermproject.repository.users.AdminRepository;
import com.ironhack.midtermproject.repository.users.ThirdPartyRepository;
import com.ironhack.midtermproject.repository.users.UserRepository;
import com.ironhack.midtermproject.service.interfaces.IBankingSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class BankingSystemService implements IBankingSystemService {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private CheckingRepository checkingRepository;
	@Autowired
	private CreditCardRepository creditCardRepository;
	@Autowired
	private SavingsRepository savingsRepository;
	@Autowired
	private StudentCheckingRepository studentCheckingRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AccountHolderRepository accountHolderRepository;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private ThirdPartyRepository thirdPartyRepository;

	public void createChecking(Integer id, BigDecimal balance, String secretKey, String status) {
		Optional<AccountHolder> accountHolder = accountHolderRepository.findById(id);

		if (accountHolder.isPresent()) {
			LocalDate birthdate = accountHolder.get().getBirthDate();
			long years = ChronoUnit.YEARS.between(birthdate, LocalDate.now());
			Money money = new Money(balance);
			if (!status.equalsIgnoreCase("ACTIVE") && !status.equalsIgnoreCase("FROZEN")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status must be either Active or Frozen");
			}
			Status s = Status.valueOf(status.toUpperCase());
			if (years >= 24) {
				checkingRepository.save(new Checking(money, accountHolder.get(), secretKey, s));
			} else {
				studentCheckingRepository.save(new StudentChecking(money, accountHolder.get(), secretKey, s));
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Primary Owner not found.");
		}
	}

	public CreditCard createCreditCard(CreditCard creditCard) {
		BigDecimal max = new BigDecimal("100000");
		BigDecimal min = new BigDecimal("100");
		BigDecimal amount = creditCard.getCreditLimit().getAmount();

		if (amount.compareTo(max) > 0 || amount.compareTo(min) < 0)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credit limit must be a value between 100 and 100000");
		else
			return creditCardRepository.save(creditCard);
	}

	public Savings createSavings(Savings savings) {
		BigDecimal max = new BigDecimal("1000");
		BigDecimal min = new BigDecimal("100");
		BigDecimal amount = savings.getMinimumBalance().getAmount();

		if (amount.compareTo(max) > 0 || amount.compareTo(min) < 0)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Minimum balance must be a value between 100 and 1000");
		else
			return savingsRepository.save(savings);
	}

	public void withdraw(Integer userId, Integer accountId, BigDecimal amount) {
		Optional<AccountHolder> accountHolder = accountHolderRepository.findById(userId);
		Optional<Account> account = accountRepository.findById(accountId);

		if (accountHolder.isPresent()) {
			if (account.isPresent()) {
				if (account.get().getPrimaryOwner().getId().equals(accountHolder.get().getId()) ||
						(account.get().getSecondaryOwner() != null &&
						account.get().getSecondaryOwner().getId().equals(accountHolder.get().getId()))) {
					account.get().getBalance().decreaseAmount(amount);
					if (account.get().getAccountType().equals(AccountType.CHECKING)) {
						Optional<Checking> checking = checkingRepository.findById(accountId);
						if (checking.get().getBalance().getAmount().compareTo(checking.get().getMinimumBalance().getAmount()) < 0) {
							checking.get().getBalance().decreaseAmount(checking.get().getPenaltyFee());
						}
					} else if (account.get().getAccountType().equals(AccountType.SAVINGS)) {
						Optional<Savings> savings = savingsRepository.findById(accountId);
						if (savings.get().getBalance().getAmount().compareTo(savings.get().getMinimumBalance().getAmount()) < 0) {
							savings.get().getBalance().decreaseAmount(savings.get().getPenaltyFee());
						}
					}
					accountRepository.save(account.get());
				} else {
					throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User cannot withdraw from that account.");
				}
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
	}

	public void deposit(Integer userId, Integer accountId, BigDecimal amount) {
		Optional<User> user = userRepository.findById(userId);
		Optional<Account> account = accountRepository.findById(accountId);

		if (user.isPresent()) {
			if (account.isPresent()) {
				account.get().getBalance().increaseAmount(amount);
					accountRepository.save(account.get());
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
	}
}
