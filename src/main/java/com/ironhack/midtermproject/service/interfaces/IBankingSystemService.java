package com.ironhack.midtermproject.service.interfaces;


import com.ironhack.midtermproject.controller.dto.accounts.AccountDTO;
import com.ironhack.midtermproject.controller.dto.accounts.CreditCardDTO;
import com.ironhack.midtermproject.controller.dto.accounts.SavingsDTO;
import com.ironhack.midtermproject.controller.dto.accounts.CheckingDTO;
import com.ironhack.midtermproject.controller.dto.users.AccountHolderDTO;
import com.ironhack.midtermproject.controller.dto.users.AdminDTO;
import com.ironhack.midtermproject.controller.dto.users.ThirdPartyDTO;
import com.ironhack.midtermproject.model.accounts.Checking;
import com.ironhack.midtermproject.model.accounts.CreditCard;
import com.ironhack.midtermproject.model.accounts.Savings;
import com.ironhack.midtermproject.model.accounts.StudentChecking;
import com.ironhack.midtermproject.model.other.Transaction;
import com.ironhack.midtermproject.model.users.AccountHolder;
import com.ironhack.midtermproject.model.users.Admin;
import com.ironhack.midtermproject.model.users.ThirdParty;

import java.math.BigDecimal;
import java.util.Optional;

public interface IBankingSystemService {
	Optional<Checking> findCheckingById(Integer id);
	Optional<CreditCard> findCreditCardById(Integer id);
	Optional<Savings> findSavingsById(Integer id);
	Optional<StudentChecking> findStudentCheckingById(Integer id);

	void createChecking(CheckingDTO CheckingDTO, Optional<Integer> secondaryOwnerId);
	CreditCard createCreditCard(CreditCardDTO creditCardDTO, BigDecimal creditLimit,
								BigDecimal interestRate, Optional<Integer> secondaryOwnerId);
	Savings createSavings(SavingsDTO savingsDTO, BigDecimal minimumBalance, BigDecimal interestRate,
						  Optional<Integer> secondaryOwnerId);
	AccountHolder createAccountHolder (AccountHolderDTO accountHolderDTO, Optional<String> email);
	Admin createAdmin (AdminDTO adminDTO);
	ThirdParty createThirdParty (ThirdPartyDTO thirdPartyDTO);

	void withdraw(Integer userId, Integer accountId, BigDecimal amount);
	void deposit(Integer userId, Integer accountId, BigDecimal amount);
	void modify(Integer accountId, BigDecimal amount);
	void thirdParty(AccountDTO accountDTO, String hashedKey);

	void savingsInterest(Integer id);
	void creditCardInterest(Integer id);

	void fraudDetection(Transaction transaction);
}
