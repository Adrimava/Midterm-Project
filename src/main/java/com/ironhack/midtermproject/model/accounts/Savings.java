package com.ironhack.midtermproject.model.accounts;

import com.ironhack.midtermproject.Money;
import com.ironhack.midtermproject.enums.AccountType;
import com.ironhack.midtermproject.enums.Status;
import com.ironhack.midtermproject.model.users.AccountHolder;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn(name = "accountId")
public class Savings extends Account{
	@NotNull
	private String secretKey;
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "amount", column = @Column(name = "minimum_balance")),
			@AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency"))
	})
	private Money minimumBalance;
	private Date creationDate;
	@NotNull
	private Status status;
	@DecimalMin(value = "0.0025", message = "Interest rate must be equal or higher than 0.0025.")
	@DecimalMax(value = "0.5", message = "Interest rate must be equal or lower than 0.5.")
	private BigDecimal interestRate;

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
	}

	/*
	**	SETTERS AND GETTERS
	 */

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public Money getMinimumBalance() {
		return minimumBalance;
	}

	public void setMinimumBalance(Money minimumBalance) {
		this.minimumBalance = minimumBalance;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	private void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
}
