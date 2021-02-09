package com.ironhack.midtermproject.controller.impl;

import com.ironhack.midtermproject.controller.interfaces.IBankingSystemController;
import com.ironhack.midtermproject.model.accounts.Checking;
import com.ironhack.midtermproject.model.accounts.CreditCard;
import com.ironhack.midtermproject.model.accounts.Savings;
import com.ironhack.midtermproject.model.accounts.StudentChecking;
import com.ironhack.midtermproject.repository.CheckingRepository;
import com.ironhack.midtermproject.repository.CreditCardRepository;
import com.ironhack.midtermproject.repository.SavingsRepository;
import com.ironhack.midtermproject.repository.StudentCheckingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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



}
