package com.ironhack.midtermproyect.model.accounts;

import com.ironhack.midtermproyect.Money;
import com.ironhack.midtermproyect.enums.Status;
import com.ironhack.midtermproyect.model.users.AccountHolder;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn(name = "accountId")
public class Savings extends Account{
	private String secretKey;
	private Money minimumBalance;
	private Date creationDate;
	private Status status;
	private BigDecimal interestRate;

	/*
	**	CONSTRUCTORS
	 */

	public Savings() {
	}

	public Savings(String secretKey, Money minimumBalance, Date creationDate,
				   Status status, BigDecimal interestRate) {
		this.secretKey = secretKey;
		this.minimumBalance = minimumBalance;
		this.creationDate = creationDate;
		this.status = status;
		this.interestRate = interestRate;
	}

	public Savings(Money balance, AccountHolder primaryOwner, Money penaltyFee,
				   String secretKey, Money minimumBalance, Date creationDate,
				   Status status, BigDecimal interestRate) {
		super(balance, primaryOwner, penaltyFee);
		this.secretKey = secretKey;
		this.minimumBalance = minimumBalance;
		this.creationDate = creationDate;
		this.status = status;
		this.interestRate = interestRate;
	}

	public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner,
				   Money penaltyFee, String secretKey, Money minimumBalance,
				   Date creationDate, Status status, BigDecimal interestRate) {
		super(balance, primaryOwner, secondaryOwner, penaltyFee);
		this.secretKey = secretKey;
		this.minimumBalance = minimumBalance;
		this.creationDate = creationDate;
		this.status = status;
		this.interestRate = interestRate;
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

	public void setCreationDate(Date creationDate) {
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
