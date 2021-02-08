package com.ironhack.midtermproyect.model.accounts;

import com.ironhack.midtermproyect.Money;
import com.ironhack.midtermproyect.model.users.AccountHolder;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CreditCard extends Account {
	private Money creditLimit;
	private Money interestRate;

	/*
	**	CONSTRUCTORS
	 */

	public CreditCard() {
	}

	public CreditCard(Money creditLimit, Money interestRate) {
		this.creditLimit = creditLimit;
		this.interestRate = interestRate;
	}

	public CreditCard(Money balance, AccountHolder primaryOwner, Money penaltyFee,
					  Money creditLimit, Money interestRate) {
		super(balance, primaryOwner, penaltyFee);
		this.creditLimit = creditLimit;
		this.interestRate = interestRate;
	}

	public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner,
					  Money penaltyFee, Money creditLimit, Money interestRate) {
		super(balance, primaryOwner, secondaryOwner, penaltyFee);
		this.creditLimit = creditLimit;
		this.interestRate = interestRate;
	}

	/*
	**	GETTERS AND SETTERS
	 */

	public Money getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Money creditLimit) {
		this.creditLimit = creditLimit;
	}

	public Money getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Money interestRate) {
		this.interestRate = interestRate;
	}
}
