package com.ironhack.midtermproject.model.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.midtermproject.Money;
import com.ironhack.midtermproject.enums.AccountType;
import com.ironhack.midtermproject.enums.Status;
import com.ironhack.midtermproject.model.users.AccountHolder;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn(name = "accountId")
public class Savings extends Account{
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "amount", column = @Column(name = "minimum_balance")),
			@AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency"))
	})
	private Money minimumBalance;
	private Status status;
	@Digits(integer = 0,fraction = 4)
	private BigDecimal interestRate;
	@JsonIgnore
	private Date lastModificationDate;

	/*
	**	CONSTRUCTORS
	 */

	public Savings() {
	}

	public Savings(Money balance, AccountHolder primaryOwner, String secretKey, Status status) {
		super(balance, primaryOwner, AccountType.SAVINGS);
		setSecretKey(secretKey);
		setMinimumBalance(new Money(new BigDecimal("1000")));
		setCreationDate(new Date());
		setStatus(status);
		setInterestRate(new BigDecimal("0.0025"));
		setLastModificationDate(super.getCreationDate());
	}

	/*
	**	SETTERS AND GETTERS
	 */

	public Money getMinimumBalance() {
		return minimumBalance;
	}

	public void setMinimumBalance(Money minimumBalance) {
		this.minimumBalance = minimumBalance;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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
