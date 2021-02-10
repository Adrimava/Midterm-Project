package com.ironhack.midtermproject.service.interfaces;


import java.math.BigDecimal;

public interface IBankingSystemService {
	void createChecking(Integer id, BigDecimal balance, String secretKey, String status);
	void withdrawChecking(Integer userId, Integer accountId, BigDecimal amount);
}
