package com.ironhack.midtermproject.service.interfaces;


import com.ironhack.midtermproject.controller.dto.CreditCardDTO;
import com.ironhack.midtermproject.controller.dto.SavingsDTO;
import com.ironhack.midtermproject.controller.dto.CheckingDTO;
import com.ironhack.midtermproject.model.accounts.Checking;
import com.ironhack.midtermproject.model.accounts.CreditCard;
import com.ironhack.midtermproject.model.accounts.Savings;
import com.ironhack.midtermproject.model.accounts.StudentChecking;

import java.math.BigDecimal;
import java.util.Optional;

public interface IBankingSystemService {
	Optional<Checking> findCheckingById(Integer id);
	Optional<CreditCard> findCreditCardById(Integer id);
	Optional<Savings> findSavingsById(Integer id);
	Optional<StudentChecking> findStudentCheckingById(Integer id);

	void createChecking(CheckingDTO CheckingDTO);
	CreditCard createCreditCard(CreditCardDTO creditCardDTO, BigDecimal creditLimit, BigDecimal interestRate);
	Savings createSavings(SavingsDTO savingsDTO, BigDecimal minimumBalance, BigDecimal interestRate);
	StudentChecking createStudentChecking(CheckingDTO studentCheckingDTO);

	void withdraw(Integer userId, Integer accountId, BigDecimal amount);
	void deposit(Integer userId, Integer accountId, BigDecimal amount);

	void savingsInterest(Integer id);
	void creditCardInterest(Integer id);
}
