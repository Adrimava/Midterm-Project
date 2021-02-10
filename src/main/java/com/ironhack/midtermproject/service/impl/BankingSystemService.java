package com.ironhack.midtermproject.service.impl;

import com.ironhack.midtermproject.Money;
import com.ironhack.midtermproject.enums.Status;
import com.ironhack.midtermproject.model.accounts.Checking;
import com.ironhack.midtermproject.model.accounts.StudentChecking;
import com.ironhack.midtermproject.model.users.AccountHolder;
import com.ironhack.midtermproject.repository.accounts.CheckingRepository;
import com.ironhack.midtermproject.repository.accounts.CreditCardRepository;
import com.ironhack.midtermproject.repository.accounts.SavingsRepository;
import com.ironhack.midtermproject.repository.accounts.StudentCheckingRepository;
import com.ironhack.midtermproject.repository.users.AccountHolderRepository;
import com.ironhack.midtermproject.repository.users.AdminRepository;
import com.ironhack.midtermproject.repository.users.ThirdPartyRepository;
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
	private CheckingRepository checkingRepository;
	@Autowired
	private CreditCardRepository creditCardRepository;
	@Autowired
	private SavingsRepository savingsRepository;
	@Autowired
	private StudentCheckingRepository studentCheckingRepository;
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

	public void withdrawChecking(Integer userId, Integer accountId, BigDecimal amount) {
		Optional<AccountHolder> accountHolder = accountHolderRepository.findById(userId);
		Optional<Checking> account = checkingRepository.findById(accountId);

		if (accountHolder.isPresent()) {
			if (account.isPresent()) {
				if (account.get().getPrimaryOwner().getId().equals(accountHolder.get().getId()) ||
						account.get().getSecondaryOwner().getId().equals(accountHolder.get().getId())) {
					account.get().getBalance().decreaseAmount(amount);
					if (account.get().getBalance().getAmount().compareTo(account.get().getMinimumBalance().getAmount()) < 0) {
						account.get().getBalance().decreaseAmount(account.get().getPenaltyFee());
					}
					checkingRepository.save(account.get());
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
}
