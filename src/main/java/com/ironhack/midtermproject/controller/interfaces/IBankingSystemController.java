package com.ironhack.midtermproject.controller.interfaces;

import com.ironhack.midtermproject.model.accounts.Checking;
import com.ironhack.midtermproject.model.accounts.CreditCard;
import com.ironhack.midtermproject.model.accounts.Savings;
import com.ironhack.midtermproject.model.accounts.StudentChecking;

import java.util.List;

public interface IBankingSystemController {

	public List<Checking> findAllChecking();
	public List<CreditCard> findAllCreditCard();
	public List<Savings> findAllSavings();
	public List<StudentChecking> findAllStudentChecking();

}
