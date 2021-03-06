package com.ironhack.midtermproject.model.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.midtermproject.model.other.Money;
import com.ironhack.midtermproject.enums.AccountType;
import com.ironhack.midtermproject.model.users.AccountHolder;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn(name = "accountId")
public class CreditCard extends Account {
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "amount", column = @Column(name = "credit_limit")),
			@AttributeOverride(name = "currency", column = @Column(name = "credit_limit_currency"))
	})
	private Money creditLimit;
	@Digits(integer = 0, fraction = 4)
	private BigDecimal interestRate;
	@JsonIgnore
	private Date lastModificationDate;
	@JsonIgnore
	private String secretKey;

	/*
	**	CONSTRUCTORS
	 */

	public CreditCard() {
	}

	public CreditCard(Money balance, AccountHolder primaryOwner) {
		super(balance, primaryOwner, AccountType.CREDIT_CARD);
		setCreditLimit(new Money(new BigDecimal("100")));
		setInterestRate(new BigDecimal("0.2"));
		setLastModificationDate(super.getCreationDate());
		setSecretKey("key");
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

	public Date getLastModificationDate() {
		return lastModificationDate;
	}

	public void setLastModificationDate(Date lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}
}
