package com.ironhack.midtermproject.controller.interfaces;

import com.ironhack.midtermproject.controller.dto.accounts.*;
import com.ironhack.midtermproject.controller.dto.users.AccountHolderDTO;
import com.ironhack.midtermproject.controller.dto.users.AdminDTO;
import com.ironhack.midtermproject.controller.dto.users.ThirdPartyDTO;
import com.ironhack.midtermproject.model.accounts.Checking;
import com.ironhack.midtermproject.model.accounts.CreditCard;
import com.ironhack.midtermproject.model.accounts.Savings;
import com.ironhack.midtermproject.model.accounts.StudentChecking;
import com.ironhack.midtermproject.model.users.AccountHolder;
import com.ironhack.midtermproject.model.users.Admin;
import com.ironhack.midtermproject.model.users.ThirdParty;

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

	void createChecking(CheckingDTO CheckingDTO, Optional<Integer> secondaryOwner);
	CreditCard createCreditCard(CreditCardDTO creditCardDTO, Optional<Integer> secondaryOwner,
								BigDecimal creditLimit, BigDecimal interestRate);
	Savings createSavings(SavingsDTO creditCardDTO, Optional<Integer> secondaryOwner,
						  BigDecimal minimumBalance, BigDecimal interestRate);
	AccountHolder createAccountHolder (AccountHolderDTO accountHolderDTO, Optional<String> email);
	Admin createAdmin (AdminDTO adminDTO);
	ThirdParty createThirdParty (ThirdPartyDTO thirdPartyDTO);

	void withdraw(Integer userId, Integer accountId, AmountDTO amountDTO);
	void deposit(Integer userId, Integer accountId, AmountDTO amountDTO);
	void transaction(Integer userId, Integer accountToWithdrawId, Integer accountToDepositId, AmountDTO amountDTO);
	void modify(Integer accountId, BalanceDTO balanceDTO);
}
