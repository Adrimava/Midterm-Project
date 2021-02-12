package com.ironhack.midtermproject.controller.interfaces;

import com.ironhack.midtermproject.controller.dto.AmountDTO;
import com.ironhack.midtermproject.controller.dto.CreditCardDTO;
import com.ironhack.midtermproject.controller.dto.SavingsDTO;
import com.ironhack.midtermproject.controller.dto.CheckingDTO;
import com.ironhack.midtermproject.model.accounts.Checking;
import com.ironhack.midtermproject.model.accounts.CreditCard;
import com.ironhack.midtermproject.model.accounts.Savings;
import com.ironhack.midtermproject.model.accounts.StudentChecking;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IBankingSystemController {

	List<Checking> findAllChecking();
	List<CreditCard> findAllCreditCard();
	List<Savings> findAllSavings();
	List<StudentChecking> findAllStudentChecking();

	Optional<Checking> findCheckingById(Integer id);
	Optional<CreditCard> findCreditCardById(Integer id);
	Optional<Savings> findSavingsById(Integer id);
	Optional<StudentChecking> findStudentCheckingById(Integer id);

	void createChecking(CheckingDTO CheckingDTO);
	CreditCard createCreditCard(CreditCardDTO creditCardDTO, BigDecimal creditLimit, BigDecimal interestRate);
	Savings createSavings(SavingsDTO creditCardDTO, BigDecimal minimumBalance, BigDecimal interestRate);
	StudentChecking createStudentChecking(CheckingDTO studentCheckingDTO);

	void withdraw(Integer userId, Integer accountId, AmountDTO amountDTO);
	void deposit(Integer userId, Integer accountId, AmountDTO amountDTO);
}
