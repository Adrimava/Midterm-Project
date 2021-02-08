package com.ironhack.midtermproyect.model.accounts;

import com.ironhack.midtermproyect.enums.Status;
import com.ironhack.midtermproyect.model.users.AccountHolder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn(name = "accountId")
public class Checking extends Account{
	private String secretKey;
	private BigDecimal minimumBalance;
	private BigDecimal monthlyMaintenanceFee;
	private Date creationDate;
	private Status status;

	/*
	**	CONSTRUCTORS
	 */

	public Checking() {
	}

	public Checking(BigDecimal balance, AccountHolder primaryOwner, BigDecimal penaltyFee, String secretKey,
					BigDecimal minimumBalance, BigDecimal monthlyMaintenanceFee, Date creationDate,
					Status status) {
		this(balance, primaryOwner, null, penaltyFee, secretKey, minimumBalance,
				monthlyMaintenanceFee, creationDate, status);
	}

	public Checking(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner,
					BigDecimal penaltyFee, String secretKey, BigDecimal minimumBalance,
					BigDecimal monthlyMaintenanceFee, Date creationDate, Status status) {
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

	public BigDecimal getMinimumBalance() {
		return minimumBalance;
	}

	public void setMinimumBalance(BigDecimal minimumBalance) {
		this.minimumBalance = minimumBalance;
	}

	public BigDecimal getMonthlyMaintenanceFee() {
		return monthlyMaintenanceFee;
	}

	public void setMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee) {
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
