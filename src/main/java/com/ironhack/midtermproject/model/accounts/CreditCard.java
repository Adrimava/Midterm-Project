package com.ironhack.midtermproject.model.accounts;

import com.ironhack.midtermproject.Money;
import com.ironhack.midtermproject.enums.AccountType;
import com.ironhack.midtermproject.model.users.AccountHolder;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
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
	@DecimalMin(value = "0.1", message = "Interest rate must be equal or higher than 0.1.")
	@DecimalMax(value = "0.2", message = "Interest rate must be equal or lower than 0.2.")
	private BigDecimal interestRate;

	/*
	**	CONSTRUCTORS
	 */

	public CreditCard() {
	}

	public CreditCard(Money balance, AccountHolder primaryOwner) {
		super(balance, primaryOwner, AccountType.CREDIT_CARD);
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
		this.creditLimit = creditLimit;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}
}
