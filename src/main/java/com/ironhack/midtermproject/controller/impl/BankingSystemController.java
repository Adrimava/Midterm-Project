package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.Money;
import com.ironhack.midtermproject.controller.dto.accounts.*;
import com.ironhack.midtermproject.controller.dto.users.AccountHolderDTO;
import com.ironhack.midtermproject.controller.dto.users.AdminDTO;
import com.ironhack.midtermproject.controller.dto.users.ThirdPartyDTO;
import com.ironhack.midtermproject.controller.interfaces.IBankingSystemController;
import com.ironhack.midtermproject.enums.Status;
import com.ironhack.midtermproject.model.accounts.Checking;
import com.ironhack.midtermproject.model.accounts.CreditCard;
import com.ironhack.midtermproject.model.accounts.Savings;
import com.ironhack.midtermproject.model.accounts.StudentChecking;
import com.ironhack.midtermproject.model.other.Address;
import com.ironhack.midtermproject.model.users.AccountHolder;
import com.ironhack.midtermproject.model.users.Admin;
import com.ironhack.midtermproject.model.users.ThirdParty;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
	public void init() throws ParseException {
		Money money1 = new Money(new BigDecimal("5555"));
		Money money2 = new Money(new BigDecimal("7777"));
		Address address1 = new Address("Gran Via", 90);
		Address address2 = new Address("Palencia", 10);
		LocalDate localDate1 = LocalDate.of(1950, 10, 10);
		LocalDate localDate2 = LocalDate.of(2000, 5, 5);
		AccountHolder accountHolder1 = new AccountHolder("Jose", localDate1, address1);
		AccountHolder accountHolder2 = new AccountHolder("Manuela", localDate2, address2);
		Admin admin = new Admin("admin");
		ThirdParty thirdParty = new ThirdParty("Pepe", "key");
		Checking checking1 = new Checking(money1, accountHolder1, "secretKey", Status.ACTIVE);
		Checking checking2 = new Checking(money2, accountHolder2, "password", Status.FROZEN);
		CreditCard creditCard1 = new CreditCard(money1, accountHolder1);
		CreditCard creditCard2 = new CreditCard(money2, accountHolder2);
		Savings savings1 = new Savings(money1, accountHolder1, "SavingsKey", Status.ACTIVE);
		Savings savings2 = new Savings(money2, accountHolder2, "SavingsPassword", Status.FROZEN);
		StudentChecking studentChecking1 = new StudentChecking(money1, accountHolder1, "StudentKey", Status.FROZEN);
		StudentChecking studentChecking2 = new StudentChecking(money2, accountHolder2, "StudentPassword", Status.ACTIVE);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date1 = sdf.parse("10-10-2019");
		savings1.setLastModificationDate(date1);
		creditCard1.setLastModificationDate(date1);
		BigDecimal amount = new BigDecimal("100");


		accountHolderRepository.save(accountHolder1);
		accountHolderRepository.save(accountHolder2);
		adminRepository.save(admin);
		thirdPartyRepository.save(thirdParty);
		checkingRepository.save(checking1);
		checkingRepository.save(checking2);
		creditCardRepository.save(creditCard1);
		creditCardRepository.save(creditCard2);
		savingsRepository.save(savings1);
		savingsRepository.save(savings2);
		studentCheckingRepository.save(studentChecking1);
		studentCheckingRepository.save(studentChecking2);


		bankingSystemService.withdraw(1, 1, amount);
//		bankingSystemService.withdraw(1, 1, amount);
//		bankingSystemService.withdraw(1, 1, amount);
//		bankingSystemService.withdraw(1, 1, amount);
//		bankingSystemService.withdraw(1, 1, amount);
//		bankingSystemService.withdraw(1, 1, amount);
		bankingSystemService.withdraw(2, 2, amount);
	}

	/*
	**	GET MAPPINGS. For ADMIN only.
	 */

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

	@GetMapping("/checking/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Optional<Checking> findCheckingById(@PathVariable Integer id) {
		return bankingSystemService.findCheckingById(id);
	}

	@GetMapping("/credit-card/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Optional<CreditCard> findCreditCardById(@PathVariable Integer id) {
		bankingSystemService.creditCardInterest(id);
		return bankingSystemService.findCreditCardById(id);
	}

	@GetMapping("/savings/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Optional<Savings> findSavingsById(@PathVariable Integer id) {
		bankingSystemService.savingsInterest(id);
		return bankingSystemService.findSavingsById(id);
	}

	@GetMapping("/student-checking/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Optional<StudentChecking> findStudentCheckingById(@PathVariable Integer id) {
		return bankingSystemService.findStudentCheckingById(id);
	}

	/*
	**	POST MAPPINGS. For ADMIN only.
	 */

	@PostMapping("/checking")
	@ResponseStatus(HttpStatus.CREATED)
	public void createChecking(@RequestBody @Valid CheckingDTO CheckingDTO,
							   @RequestParam Optional<Integer> secondaryOwner) {
		bankingSystemService.createChecking(CheckingDTO, secondaryOwner);
	}

	@PostMapping("/credit-card")
	@ResponseStatus(HttpStatus.CREATED)
	public CreditCard createCreditCard(@RequestBody @Valid CreditCardDTO creditCardDTO,
									   @RequestParam Optional<Integer> secondaryOwner,
									   @RequestParam(defaultValue = "100") BigDecimal creditLimit,
									   @RequestParam(defaultValue = "0.2") BigDecimal interestRate) {
		return bankingSystemService.createCreditCard(creditCardDTO, creditLimit, interestRate, secondaryOwner);
	}

	@PostMapping("/savings")
	@ResponseStatus(HttpStatus.CREATED)
	public Savings createSavings(@RequestBody @Valid SavingsDTO savingsDTO,
								 @RequestParam Optional<Integer> secondaryOwner,
								 @RequestParam(defaultValue = "1000") BigDecimal minimumBalance,
								 @RequestParam(defaultValue = "0.0025") BigDecimal interestRate) {
		return bankingSystemService.createSavings(savingsDTO, minimumBalance, interestRate, secondaryOwner);
	}

	@PostMapping("/account-holder")
	@ResponseStatus(HttpStatus.CREATED)
	public AccountHolder createAccountHolder(@RequestBody @Valid AccountHolderDTO accountHolderDTO,
											 @RequestParam Optional<String> email) {
		return bankingSystemService.createAccountHolder(accountHolderDTO, email);
	}

	@PostMapping("/admin")
	@ResponseStatus(HttpStatus.CREATED)
	public Admin createAdmin(@RequestBody @Valid AdminDTO adminDTO) {
		return bankingSystemService.createAdmin(adminDTO);
	}

	@PostMapping("/third-party")
	@ResponseStatus(HttpStatus.CREATED)
	public ThirdParty createThirdParty(@RequestBody @Valid ThirdPartyDTO thirdPartyDTO) {
		return bankingSystemService.createThirdParty(thirdPartyDTO);
	}

	/*
	**	PATCH MAPPING
	 */


	//Following methods are for ACCOUNT_HOLDER only.

	@PatchMapping("/withdraw/{userId}/{accountId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void withdraw(@PathVariable Integer userId, @PathVariable Integer accountId,
						 @RequestBody @Valid AmountDTO amountDTO) {
		bankingSystemService.withdraw(userId, accountId, amountDTO.getAmount());
	}

	@PatchMapping("/deposit/{userId}/{accountId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deposit(@PathVariable Integer userId, @PathVariable Integer accountId,
						 @RequestBody @Valid AmountDTO amountDTO) {
		bankingSystemService.deposit(userId, accountId, amountDTO.getAmount());
	}

	@PatchMapping("/transaction/{userId}/{accountToWithdrawId}/{accountToDepositId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void transaction(@PathVariable Integer userId, @PathVariable Integer accountToWithdrawId,
						@PathVariable Integer accountToDepositId ,@RequestBody @Valid AmountDTO amountDTO) {
		bankingSystemService.withdraw(userId, accountToWithdrawId, amountDTO.getAmount());
		bankingSystemService.deposit(userId, accountToDepositId, amountDTO.getAmount());
	}

	//Following methods are for ADMIN only.

	@PatchMapping("/modify/{accountId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void modify(@PathVariable Integer accountId, @RequestBody @Valid BalanceDTO balanceDTO) {
		bankingSystemService.modify(accountId, balanceDTO.getAmount());
	}

	//Following methods are for THIRD_PARTY only.

	@PatchMapping("/third-party/{hashedKey}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void thirdParty(@RequestBody @Valid AccountDTO accountDTO, @PathVariable String hashedKey) {
		bankingSystemService.thirdParty(accountDTO, hashedKey);
	}
}
