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

	public CreditCard(Money balance, AccountHolder primaryOwner) {
		super(balance, primaryOwner);
		setCreditLimit(new Money(new BigDecimal("100")));
		setInterestRate(new BigDecimal("0.2"));
	}

	/*
	**	GETTERS AND SETTERS
	 */

	public Money getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Money creditLimit) {
		BigDecimal max = new BigDecimal("100000");
		BigDecimal min = new BigDecimal("100");
		BigDecimal amount = creditLimit.getAmount();

		if (amount.compareTo(max) > 0 || amount.compareTo(min) < 0)
			throw new IllegalArgumentException("Credit limit must be a value between 100 and 1000000");
		else
			this.creditLimit = creditLimit;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		BigDecimal max = new BigDecimal("0.2");
		BigDecimal min = new BigDecimal("0.1");

		if (interestRate.compareTo(max) > 0 || interestRate.compareTo(min) < 0)
			throw new IllegalArgumentException("Interest rate must be a value between 0.2 and 0.1.");
		else
			this.interestRate = interestRate;
	}
}
