package com.ironhack.midtermproject.service.impl;

import com.ironhack.midtermproject.Money;
import com.ironhack.midtermproject.controller.dto.accounts.AccountDTO;
import com.ironhack.midtermproject.controller.dto.accounts.CreditCardDTO;
import com.ironhack.midtermproject.controller.dto.accounts.SavingsDTO;
import com.ironhack.midtermproject.controller.dto.accounts.CheckingDTO;
import com.ironhack.midtermproject.controller.dto.users.AccountHolderDTO;
import com.ironhack.midtermproject.controller.dto.users.AdminDTO;
import com.ironhack.midtermproject.controller.dto.users.ThirdPartyDTO;
import com.ironhack.midtermproject.enums.AccountType;
import com.ironhack.midtermproject.enums.Status;
import com.ironhack.midtermproject.model.accounts.*;
import com.ironhack.midtermproject.model.other.Transaction;
import com.ironhack.midtermproject.model.users.AccountHolder;
import com.ironhack.midtermproject.model.users.Admin;
import com.ironhack.midtermproject.model.users.ThirdParty;
import com.ironhack.midtermproject.model.users.User;
import com.ironhack.midtermproject.repository.accounts.*;
import com.ironhack.midtermproject.repository.other.TransactionRepository;
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
import java.util.List;
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
	@Autowired
	private TransactionRepository transactionRepository;

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

	/*
	**	createChecking method validates the inputs, and if they are correct, it creates either a
	**	student checking account if the account holder is less than 24 years old, or a checking account
	**	otherwise. It also checks for the optional parameters (secondary owner), and if it exists, it will
	**	be set inside the created account.
	 */
	public void createChecking(CheckingDTO checkingDTO, Optional<Integer> secondaryOwnerId) {
		Optional<AccountHolder> accountHolder = accountHolderRepository.findById(checkingDTO.getUserId());

		if (accountHolder.isPresent()) {
			LocalDate birthdate = accountHolder.get().getBirthDate();
			long years = ChronoUnit.YEARS.between(birthdate, LocalDate.now());
			Money money = new Money(checkingDTO.getBalance());
			if (!checkingDTO.getStatus().equalsIgnoreCase("ACTIVE") &&
					!checkingDTO.getStatus().equalsIgnoreCase("FROZEN")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status must be either Active or Frozen");
			}
			Status status = Status.valueOf(checkingDTO.getStatus().toUpperCase());
			if (years >= 24) {
				Checking checking = new Checking(money, accountHolder.get(), checkingDTO.getSecretKey(), status);
				if (secondaryOwnerId.isPresent()) {
					Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(secondaryOwnerId.get());
					if (secondaryOwner.isPresent()) {
						checking.setSecondaryOwner(secondaryOwner.get());
					} else {
						throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Secondary Owner not found.");
					}
				}
				checkingRepository.save(checking);
			} else {
				StudentChecking studentChecking = new StudentChecking(
						money, accountHolder.get(), checkingDTO.getSecretKey(), status);
				if (secondaryOwnerId.isPresent()) {
					Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(secondaryOwnerId.get());
					if (secondaryOwner.isPresent()) {
						studentChecking.setSecondaryOwner(secondaryOwner.get());
					} else {
						throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Secondary Owner not found.");
					}
				}
				studentCheckingRepository.save(studentChecking);
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Primary Owner not found.");
		}
	}

	/*
	**	createCreditCard method validates the inputs, and if they are correct, it creates a
	**	credit card account. It also checks for the optional parameters (secondary owner,
	**	credit limit and interestRate),	and if they exist, they will be set inside the created account.
	 */
	public CreditCard createCreditCard(CreditCardDTO creditCardDTO, BigDecimal creditLimit,
									   BigDecimal interestRate, Optional<Integer> secondaryOwnerId) {
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
			if (secondaryOwnerId.isPresent()) {
				Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(secondaryOwnerId.get());
				if (secondaryOwner.isPresent()) {
					creditCard.setSecondaryOwner(secondaryOwner.get());
				} else {
					throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Secondary Owner not found.");
				}
			}
			return creditCardRepository.save(creditCard);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
	}

	/*
	**	createSavings method validates the inputs, and if they are correct, it creates a
	**	savings account. It also checks for the optional parameters (secondary owner,
	**	minimum balance and interestRate), and if they exist, they will be set inside the created account.
	 */
	public Savings createSavings(SavingsDTO savingsDTO, BigDecimal minimumBalance,
								 BigDecimal interestRate, Optional<Integer> secondaryOwnerId) {
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
			if (secondaryOwnerId.isPresent()) {
				Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(secondaryOwnerId.get());
				if (secondaryOwner.isPresent()) {
					savings.setSecondaryOwner(secondaryOwner.get());
				} else {
					throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Secondary Owner not found.");
				}
			}
			return savingsRepository.save(savings);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
	}

	public AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO, Optional<String> email) {
		AccountHolder accountHolder = new AccountHolder(accountHolderDTO.getName(),
				accountHolderDTO.getBirthdate(), accountHolderDTO.getAddress());
		if (email.isPresent()) {
			accountHolder.setEmail(email.get());
		}
		return accountHolderRepository.save(accountHolder);
	}

	public Admin createAdmin(AdminDTO adminDTO) {
		return adminRepository.save(new Admin(adminDTO.getName()));
	}

	public ThirdParty createThirdParty(ThirdPartyDTO thirdPartyDTO) {
		return thirdPartyRepository.save(new ThirdParty(thirdPartyDTO.getName(), thirdPartyDTO.getHashedKey()));
	}

	/*
	**	WITHDRAW METHOD
	 */


	/*
	**	withdraw method accepts an user, an account and an amount. It checks if the user and the account
	**	exist and if they do, it checks if the user can withdraw that amount of money from that account.
	**	Depending on the account type, if the money left is below a certain minimum, a penalty fee will
	**	be charged.
	**	fraudDetection method is called to avoid some cases of possible fraud.
	 */
	public void withdraw(Integer userId, Integer accountId, BigDecimal amount) {
		Optional<AccountHolder> accountHolder = accountHolderRepository.findById(userId);
		Optional<Account> account = accountRepository.findById(accountId);

		if (accountHolder.isPresent()) {
			if (account.isPresent()) {
				if (account.get().getPrimaryOwner().getId().equals(accountHolder.get().getId()) ||
						(account.get().getSecondaryOwner() != null &&
						account.get().getSecondaryOwner().getId().equals(accountHolder.get().getId()))) {
					if (amount.compareTo(account.get().getBalance().getAmount()) < 0) {
						account.get().getBalance().decreaseAmount(amount);
						if (account.get().getAccountType().equals(AccountType.CHECKING)) {
							Optional<Checking> checking = checkingRepository.findById(accountId);
							if (checking.get().getBalance().getAmount().compareTo(
									checking.get().getMinimumBalance().getAmount()) < 0) {
								checking.get().getBalance().decreaseAmount(checking.get().getPenaltyFee());
							}
						} else if (account.get().getAccountType().equals(AccountType.SAVINGS)) {
							savingsInterest(accountId);
							Optional<Savings> savings = savingsRepository.findById(accountId);
							if (savings.get().getBalance().getAmount().compareTo(
									savings.get().getMinimumBalance().getAmount()) < 0) {
								savings.get().getBalance().decreaseAmount(savings.get().getPenaltyFee());
							}
						} else if (account.get().getAccountType().equals(AccountType.CREDIT_CARD)) {
							creditCardInterest(accountId);
						}
						Transaction transaction = transactionRepository.save(
								new Transaction(userId, accountId, new Money(amount.negate())));
						fraudDetection(transaction);
						accountRepository.save(account.get());
					} else {
						throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough balance in that account.");
					}
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

	/*
	**	deposit method accepts an user, an account and an amount. It checks if the user and the account
	**	exist and if they do, the user can deposit that amount of money to that account.
	**	fraudDetection method is called to avoid some cases of possible fraud.
	 */
	public void deposit(Integer userId, Integer accountId, BigDecimal amount) {
		Optional<User> user = userRepository.findById(userId);
		Optional<Account> account = accountRepository.findById(accountId);

		if (user.isPresent()) {
			if (account.isPresent()) {
				account.get().getBalance().increaseAmount(amount);
				Transaction transaction = transactionRepository.save(
						new Transaction(userId, accountId, new Money(amount.negate())));
				fraudDetection(transaction);
				accountRepository.save(account.get());
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
	}

	/*
	**	MODIFY ACCOUNT BALANCE METHODS
	 */

	/*
	**	modify method accepts an account and an amount. It checks if the account
	**	exist and if it does, the admin can modify that amount of money in that account.
	 */
	public void modify(Integer accountId, BigDecimal amount) {
		Optional<Account> account = accountRepository.findById(accountId);

		if (account.isPresent()) {
			account.get().getBalance().increaseAmount(amount);
			accountRepository.save(account.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
		}
	}

	/*
	**	thirdParty method accepts account info and a hashed key. It checks if the account
	**	exist and if it does it checks the key. Then, the third can modify that amount of money
	**	in that account.
	 */
	public void thirdParty(AccountDTO accountDTO, String hashedKey) {
		Optional<Account> account = accountRepository.findById(accountDTO.getAccountId());

		if (account.isPresent()) {
			if (accountDTO.getAccountSecretKey().equals(account.get().getSecretKey())) {
				account.get().getBalance().increaseAmount(accountDTO.getAmount());
				accountRepository.save(account.get());
			} else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Key is not correct.");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
		}
	}

	/*
	**	INTEREST METHODS
	 */

	/*
	**	savingInterest method checks it a savings account should increase by a yearly interest its
	**	balance.
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

	/*
	**	creditCardInterest method checks it a credit card account should increase by a yearly interest its
	**	balance.
	 */
	public void creditCardInterest(Integer id) {
		Optional<CreditCard> creditCard = creditCardRepository.findById(id);

		if (creditCard.isPresent()) {
			long months = Math.abs(new Date().getTime() - creditCard.get().getLastModificationDate().getTime());
			months = TimeUnit.DAYS.convert(months, TimeUnit.MILLISECONDS) / 30;
			if (months > 0) {
				BigDecimal interest = (new BigDecimal("1"));
				for (long i = 0; i < months; i++) {
					interest = interest.multiply(creditCard.get().getInterestRate().add(new BigDecimal("1")));
				}
				BigDecimal newBalance = creditCard.get().getBalance().getAmount().multiply(interest);
				interest = newBalance.subtract(creditCard.get().getBalance().getAmount());
				creditCard.get().getBalance().increaseAmount(interest);
				creditCard.get().setLastModificationDate(new Date());
				creditCardRepository.save(creditCard.get());
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
		}
	}

	/*
	**	FRAUD DETECTION METHOD
	 */

	/*
	**	fraudDetection method is called everytime there is a deposit, a withdrawal, or a transaction.
	**	It first checks if more than two movements have been done in a certain account in less than a second.
	**	Then it checks if the total amount of transactions is over a 150% of a daily highest transaction count.
	 */
	public void fraudDetection(Transaction transaction) {
		List<Transaction> transactionList = transactionRepository.findByAccountId(transaction.getAccountId());

		if (transactionList.size() > 2) {
			Transaction previousTransaction = transactionList.get(transactionList.size() - 3);
			long difference = Math.abs(transaction.getDate() - previousTransaction.getDate());
			if (difference < 1000)
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"This transaction may be fraudulent. Cause: More than two transactions per second.");
		}

		Long now = transaction.getDate();
		Long then = (now - 1000 * 60 * 60 * 24);
		transactionList = transactionRepository.findByDateBetween(then, now);
		if (transactionList.size() > Transaction.getHighestDailyTotalTransactions() * 1.5) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					"This transaction may be fraudulent. Cause: Unusual amount of transactions in last 24h period.");
		}
	}

}
