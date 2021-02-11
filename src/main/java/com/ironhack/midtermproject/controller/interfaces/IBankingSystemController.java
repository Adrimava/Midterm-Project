package com.ironhack.midtermproject.controller.interfaces;

import com.ironhack.midtermproject.controller.dto.AmountDTO;
import com.ironhack.midtermproject.model.accounts.Checking;
import com.ironhack.midtermproject.model.accounts.CreditCard;
import com.ironhack.midtermproject.model.accounts.Savings;
import com.ironhack.midtermproject.model.accounts.StudentChecking;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;

public interface IBankingSystemController {

	List<Checking> findAllChecking();
	List<CreditCard> findAllCreditCard();
	List<Savings> findAllSavings();
	List<StudentChecking> findAllStudentChecking();
	Checking createChecking(Checking checking);
	CreditCard createCreditCard(CreditCard creditCard);
	Savings createSavings(Savings savings);
	StudentChecking createStudentChecking(StudentChecking studentChecking);
	void createChecking(Integer id, BigDecimal balance, String secretKey, String status);
	void withdraw(Integer userId, Integer accountId, AmountDTO amountDTO);
	void deposit(Integer userId, Integer accountId, AmountDTO amountDTO);
}
