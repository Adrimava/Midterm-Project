package com.ironhack.midtermproject.service.interfaces;


import com.ironhack.midtermproject.model.accounts.CreditCard;
import com.ironhack.midtermproject.model.accounts.Savings;

import java.math.BigDecimal;

public interface IBankingSystemService {
	void createChecking(Integer id, BigDecimal balance, String secretKey, String status);
	CreditCard createCreditCard(CreditCard creditCard);
	Savings createSavings(Savings savings);
	void withdraw(Integer userId, Integer accountId, BigDecimal amount);
	void deposit(Integer userId, Integer accountId, BigDecimal amount);
}
