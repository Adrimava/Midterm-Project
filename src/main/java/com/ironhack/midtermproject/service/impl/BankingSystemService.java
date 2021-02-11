package com.ironhack.midtermproject.service.impl;

import com.ironhack.midtermproject.Money;
import com.ironhack.midtermproject.controller.dto.CreditCardDTO;
import com.ironhack.midtermproject.controller.dto.SavingsDTO;
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
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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

	/*
	**	FIND METHODS
	 */

	public Optional<Checking> findCheckingById(Integer id) {
		Optional<Checking> checking = checkingRepository.findById(id);

		if (checking.isPresent()) {
			return checking;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking not found.");
		}
	}

	public Optional<CreditCard> findCreditCardById(Integer id) {
		Optional<CreditCard> creditCard = creditCardRepository.findById(id);

		if (creditCard.isPresent()) {
			return creditCard;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CreditCard not found.");
		}
	}

	public Optional<Savings> findSavingsById(Integer id) {
		Optional<Savings> savings = savingsRepository.findById(id);

		if (savings.isPresent()) {
			return savings;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Savings not found.");
		}
	}

	public Optional<StudentChecking> findStudentCheckingById(Integer id) {
		Optional<StudentChecking> studentChecking = studentCheckingRepository.findById(id);

		if (studentChecking.isPresent()) {
			return studentChecking;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "StudentChecking not found.");
		}
	}

	/*
	**	CREATE METHODS
	 */

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

	public CreditCard createCreditCard(CreditCardDTO creditCardDTO, BigDecimal creditLimit, BigDecimal interestRate) {
		Optional<AccountHolder> accountHolder = accountHolderRepository.findById(creditCardDTO.getUserId());

		if (accountHolder.isPresent()) {
			if (creditLimit.compareTo(new BigDecimal("100")) < 0 ||
					creditLimit.compareTo(new BigDecimal("100000")) > 0 )
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credit limit must be a value between 100 and 100000");
			if (interestRate.compareTo(new BigDecimal("0.1")) < 0 ||
					interestRate.compareTo(new BigDecimal("0.2")) > 0 )
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Interest rate must be a value between 0.1 and 0.2");
			CreditCard creditCard = new CreditCard(new Money(creditCardDTO.getMoney()), accountHolder.get());
			creditCard.setCreditLimit(new Money(creditLimit));
			creditCard.setInterestRate(interestRate);
			return creditCardRepository.save(creditCard);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
	}

	public Savings createSavings(SavingsDTO savingsDTO, BigDecimal minimumBalance, BigDecimal interestRate) {
		Optional<AccountHolder> accountHolder = accountHolderRepository.findById(savingsDTO.getUserId());

		if (accountHolder.isPresent()) {
			if (minimumBalance.compareTo(new BigDecimal("100")) < 0 ||
					minimumBalance.compareTo(new BigDecimal("1000")) > 0 )
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credit limit must be a value between 100 and 1000");
			if (interestRate.compareTo(new BigDecimal("0.0025")) < 0 ||
					interestRate.compareTo(new BigDecimal("0.5")) > 0 )
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Interest rate must be a value between 0.0025 and 0.5");
			if (!savingsDTO.getStatus().equalsIgnoreCase("ACTIVE") &&
					!savingsDTO.getStatus().equalsIgnoreCase("FROZEN")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status must be either Active or Frozen");
			}
			Savings savings = new Savings(new Money(savingsDTO.getBalance()), accountHolder.get(),
					savingsDTO.getSecretKey(), Status.valueOf(savingsDTO.getStatus().toUpperCase()));
			savings.setMinimumBalance(new Money(minimumBalance));
			savings.setInterestRate(interestRate);
			return savingsRepository.save(savings);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
	}

	/*
	**	WITHDRAW METHOD
	 */

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
					}
					else if (account.get().getAccountType().equals(AccountType.SAVINGS)) {
						//savingsInterest(accountId);
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

	/*
	**	DEPOSIT METHOD
	 */

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

	/*
	**	INTEREST METHODS
	 */

	public void savingsInterest(Integer id) {
		Optional<Savings> savings = savingsRepository.findById(id);

		if (savings.isPresent()) {
			long years = Math.abs(new Date().getTime() - savings.get().getLastModificationDate().getTime());
			years = TimeUnit.DAYS.convert(years, TimeUnit.MILLISECONDS) / 365;
			if (years > 0) {
				BigDecimal interest = (new BigDecimal("1"));
				for (long i = 0; i < years; i++) {
					interest = interest.multiply(savings.get().getInterestRate().add(new BigDecimal("1")));
				}
				BigDecimal newBalance = savings.get().getBalance().getAmount().multiply(interest);
				interest = newBalance.subtract(savings.get().getBalance().getAmount());
				savings.get().getBalance().increaseAmount(interest);
				savings.get().setLastModificationDate(new Date());
				savingsRepository.save(savings.get());
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
		}
	}
}
