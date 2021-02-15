package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.model.other.Money;
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
