package com.ironhack.midtermproyect.model.accounts;

import com.ironhack.midtermproyect.Money;
import com.ironhack.midtermproyect.model.users.AccountHolder;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@PrimaryKeyJoinColumn(name = "accountId")
public class CreditCard extends Account {
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "amount", column = @Column(name = "credit_limit")),
			@AttributeOverride(name = "currency", column = @Column(name = "credit_limit_currency"))
	})
	private Money creditLimit;
	private BigDecimal interestRate;

	/*
	**	CONSTRUCTORS
	 */

	public CreditCard() {
	}

	public CreditCard(Money balance, AccountHolder primaryOwner, Money penaltyFee,
					  Money creditLimit, BigDecimal interestRate) {
		this(balance, primaryOwner, null, penaltyFee, creditLimit, interestRate);
	}

	public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner,
					  Money penaltyFee, Money creditLimit, BigDecimal interestRate) {
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

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}
}
