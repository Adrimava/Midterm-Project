package com.ironhack.midtermproyect.model.accounts;

import com.ironhack.midtermproyect.model.users.AccountHolder;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.math.BigDecimal;

@Entity
@PrimaryKeyJoinColumn(name = "accountId")
public class CreditCard extends Account {
	private BigDecimal creditLimit;
	private BigDecimal interestRate;

	/*
	**	CONSTRUCTORS
	 */

	public CreditCard() {
	}

	public CreditCard(BigDecimal balance, AccountHolder primaryOwner, BigDecimal penaltyFee,
					  BigDecimal creditLimit, BigDecimal interestRate) {
		this(balance, primaryOwner, null, penaltyFee, creditLimit, interestRate);
	}

	public CreditCard(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner,
					  BigDecimal penaltyFee, BigDecimal creditLimit, BigDecimal interestRate) {
		super(balance, primaryOwner, secondaryOwner, penaltyFee);
		this.creditLimit = creditLimit;
		this.interestRate = interestRate;
	}

	/*
	**	GETTERS AND SETTERS
	 */

	public BigDecimal getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}
}
