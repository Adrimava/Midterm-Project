package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.Money;
import com.ironhack.midtermproject.controller.dto.AmountDTO;
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
import com.ironhack.midtermproject.service.interfaces.IBankingSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
public class BankingSystemController implements IBankingSystemController {

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
	@Autowired
	private IBankingSystemService bankingSystemService;


	/////////////////////////////////	JUST FOR TESTING PURPOSES	////////////////////////////////////////////
	@GetMapping("/init")
	public void init() {
		Money money1 = new Money(new BigDecimal("5555"));
		Money money2 = new Money(new BigDecimal("7777"));
		Address address1 = new Address("Gran Via", 90);
		Address address2 = new Address("Palencia", 10);
		LocalDate localDate1 = LocalDate.of(1950, 10, 10);
		LocalDate localDate2 = LocalDate.of(2000, 5, 5);
		AccountHolder accountHolder1 = new AccountHolder("Jose Perez", localDate1, address1);
		AccountHolder accountHolder2 = new AccountHolder("Manuela Garcia", localDate2, address2);
		Checking checking1 = new Checking(money1, accountHolder1, "secretKey", Status.ACTIVE);
		Checking checking2 = new Checking(money2, accountHolder2, "password", Status.FROZEN);
		CreditCard creditCard1 = new CreditCard(money1, accountHolder1);
		CreditCard creditCard2 = new CreditCard(money2, accountHolder2);
		Savings savings1 = new Savings(money1, accountHolder1, "SavingsKey", Status.ACTIVE);
		Savings savings2 = new Savings(money2, accountHolder2, "SavingsPassword", Status.FROZEN);
		StudentChecking studentChecking1 = new StudentChecking(money1, accountHolder1, "StudentKey", Status.FROZEN);
		StudentChecking studentChecking2 = new StudentChecking(money2, accountHolder2, "StudentPassword", Status.ACTIVE);


		accountHolderRepository.save(accountHolder1);
		accountHolderRepository.save(accountHolder2);
		checkingRepository.save(checking1);
		checkingRepository.save(checking2);
		creditCardRepository.save(creditCard1);
		creditCardRepository.save(creditCard2);
		savingsRepository.save(savings1);
		savingsRepository.save(savings2);
		studentCheckingRepository.save(studentChecking1);
		studentCheckingRepository.save(studentChecking2);
	}



	@GetMapping("/checking")
	@ResponseStatus(HttpStatus.OK)
	public List<Checking> findAllChecking() {

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
	public Checking storeChecking(@RequestBody @Valid Checking checking) {
		return checkingRepository.save(checking);
	}

	@PostMapping("/credit-card")
	@ResponseStatus(HttpStatus.CREATED)
	public CreditCard storeCreditCard(@RequestBody @Valid CreditCard creditCard) {
		return creditCardRepository.save(creditCard);
	}

	@PostMapping("/savings")
	@ResponseStatus(HttpStatus.CREATED)
	public Savings storeSavings(@RequestBody @Valid Savings savings) {
		return savingsRepository.save(savings);
	}

	@PostMapping("/student-checking")
	@ResponseStatus(HttpStatus.CREATED)
	public StudentChecking storeStudentChecking(@RequestBody @Valid StudentChecking studentChecking) {
		return studentCheckingRepository.save(studentChecking);
	}

	@PostMapping("/checking/create/{id}/{balance}/{secretKey}/{status}")
	@ResponseStatus(HttpStatus.CREATED)
	public void createChecking(@PathVariable Integer id, @PathVariable BigDecimal balance,
							   @PathVariable String secretKey, @PathVariable String status) {
		bankingSystemService.createChecking(id,balance,secretKey,status);
	}

	@PatchMapping("/checking/withdraw/{userId}/{accountId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void withdrawChecking(@PathVariable Integer userId, @PathVariable Integer accountId,
								 @RequestBody AmountDTO amountDTO) {
		bankingSystemService.withdrawChecking(userId, accountId, amountDTO.getAmount());
	}
}
