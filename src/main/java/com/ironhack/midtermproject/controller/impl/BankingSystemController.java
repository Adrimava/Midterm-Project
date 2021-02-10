package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.Money;
import com.ironhack.midtermproject.controller.interfaces.IBankingSystemController;
import com.ironhack.midtermproject.enums.Status;
import com.ironhack.midtermproject.model.accounts.Checking;
import com.ironhack.midtermproject.model.accounts.CreditCard;
import com.ironhack.midtermproject.model.accounts.Savings;
import com.ironhack.midtermproject.model.accounts.StudentChecking;
import com.ironhack.midtermproject.model.other.Address;
import com.ironhack.midtermproject.model.users.AccountHolder;
import com.ironhack.midtermproject.repository.accounts.CheckingRepository;
import com.ironhack.midtermproject.repository.accounts.CreditCardRepository;
import com.ironhack.midtermproject.repository.accounts.SavingsRepository;
import com.ironhack.midtermproject.repository.accounts.StudentCheckingRepository;
import com.ironhack.midtermproject.repository.users.AccountHolderRepository;
import com.ironhack.midtermproject.repository.users.AdminRepository;
import com.ironhack.midtermproject.repository.users.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
public class BankingSystemController implements IBankingSystemController {
	Money money1 = new Money(new BigDecimal("5555"));
	Money money2 = new Money(new BigDecimal("7777"));
	Address address1 = new Address("Gran Via", 90);
	Address address2 = new Address("Palencia", 10);
	LocalDate localDate = null;
	AccountHolder accountHolder1 = new AccountHolder("Jose Perez", localDate, address1);
	AccountHolder accountHolder2 = new AccountHolder("Manuela Garcia", localDate, address1);
	Checking checking1 = new Checking(money1, accountHolder1, "secretKey", Status.ACTIVE);
	Checking checking2 = new Checking(money2, accountHolder2, "password", Status.FROZEN);


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

	@GetMapping("/checking")
	@ResponseStatus(HttpStatus.OK)
	public List<Checking> findAllChecking() {

		accountHolderRepository.save(accountHolder1);
		accountHolderRepository.save(accountHolder2);
		checkingRepository.save(checking1);
		checkingRepository.save(checking2);

		return checkingRepository.findAll();
	}

	@GetMapping("/credit-card")
	@ResponseStatus(HttpStatus.OK)
	public List<CreditCard> findAllCreditCard() {
		return creditCardRepository.findAll();
	}

	@GetMapping("/savings")
	@ResponseStatus(HttpStatus.OK)
	public List<Savings> findAllSavings() {
		return savingsRepository.findAll();
	}

	@GetMapping("/student-checking")
	@ResponseStatus(HttpStatus.OK)
	public List<StudentChecking> findAllStudentChecking() {
		return studentCheckingRepository.findAll();
	}

	@PostMapping("/checking")
	@ResponseStatus(HttpStatus.CREATED)
	public Checking store() {

		accountHolderRepository.save(accountHolder1);

		return checkingRepository.save(checking1);
	}
}
