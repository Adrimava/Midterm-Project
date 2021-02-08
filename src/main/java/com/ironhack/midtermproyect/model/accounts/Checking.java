package com.ironhack.midtermproyect.model.accounts;

import com.ironhack.midtermproyect.Money;
import com.ironhack.midtermproyect.enums.Status;
import com.ironhack.midtermproyect.model.users.AccountHolder;

import javax.persistence.*;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn(name = "accountId")
public class Checking extends Account{
	private String secretKey;
	private Money minimumBalance;
	private Money monthlyMaintenanceFee;
	private Date creationDate;
	private Status status;

	/*
	**	CONSTRUCTORS
	 */

	public Checking() {
	}

	public Checking(Money balance, AccountHolder primaryOwner, Money penaltyFee, String secretKey,
					Money minimumBalance, Money monthlyMaintenanceFee, Date creationDate,
					Status status) {
		this(balance, primaryOwner, null, penaltyFee, secretKey, minimumBalance,
				monthlyMaintenanceFee, creationDate, status);
	}

	public Checking(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner,
					Money penaltyFee, String secretKey, Money minimumBalance,
					Money monthlyMaintenanceFee, Date creationDate, Status status) {
		super(balance, primaryOwner, secondaryOwner, penaltyFee);
		this.secretKey = secretKey;
		this.minimumBalance = minimumBalance;
		this.monthlyMaintenanceFee = monthlyMaintenanceFee;
		this.creationDate = creationDate;
		this.status = status;
	}

	/*
	**	GETTERS AND SETTERS
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

	public Money getMonthlyMaintenanceFee() {
		return monthlyMaintenanceFee;
	}

	public void setMonthlyMaintenanceFee(Money monthlyMaintenanceFee) {
		this.monthlyMaintenanceFee = monthlyMaintenanceFee;
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
}
