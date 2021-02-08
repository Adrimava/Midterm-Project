package com.ironhack.midtermproyect.model.accounts;

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
	private BigDecimal minimumBalance;
	private Date creationDate;
	private Status status;
	private BigDecimal interestRate;

	/*
	**	CONSTRUCTORS
	 */

	public Savings() {
	}

	/*
	public Savings(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner,
				   BigDecimal penaltyFee, String secretKey,
				   Date creationDate, Status status) {
		this(balance, primaryOwner, secondaryOwner, penaltyFee, secretKey,
				new BigDecimal("1000"), creationDate, status, new BigDecimal("0.0025"));
	}

	public Savings(BigDecimal balance, AccountHolder primaryOwner, BigDecimal penaltyFee,
				   String secretKey, Date creationDate,
				   Status status) {
		this(balance, primaryOwner, null, penaltyFee, secretKey,
				new BigDecimal("1000"), creationDate, status, new BigDecimal("0.0025"));
	}

	public Savings(BigDecimal balance, AccountHolder primaryOwner, BigDecimal penaltyFee,
				   String secretKey, Date creationDate,
				   Status status, BigDecimal interestRate) {
		this(balance, primaryOwner, null, penaltyFee, secretKey,
				new BigDecimal("1000"), creationDate, status, interestRate);
	}

	public Savings(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner,
				   BigDecimal penaltyFee, String secretKey,
				   Date creationDate, Status status, BigDecimal interestRate) {
		this(balance, primaryOwner, secondaryOwner, penaltyFee, secretKey,
				new BigDecimal("1000"), creationDate, status, interestRate);
	}

	public Savings(BigDecimal balance, AccountHolder primaryOwner, BigDecimal penaltyFee,
				   String secretKey, BigDecimal minimumBalance, Date creationDate,
				   Status status) {
		this(balance, primaryOwner, null, penaltyFee, secretKey,
				minimumBalance, creationDate, status, new BigDecimal("0.0025"));
	}

	public Savings(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner,
				   BigDecimal penaltyFee, String secretKey, BigDecimal minimumBalance,
				   Date creationDate, Status status) {
		this(balance, primaryOwner, secondaryOwner, penaltyFee, secretKey,
				minimumBalance, creationDate, status, new BigDecimal("0.0025"));
	}

	public Savings(BigDecimal balance, AccountHolder primaryOwner, BigDecimal penaltyFee,
				   String secretKey, BigDecimal minimumBalance, Date creationDate,
				   Status status, BigDecimal interestRate) {
		this(balance, primaryOwner, null, penaltyFee, secretKey,
				minimumBalance, creationDate, status, interestRate);
	}
	*/

	public Savings(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner,
				   BigDecimal penaltyFee, String secretKey, BigDecimal minimumBalance,
				   Date creationDate, Status status, BigDecimal interestRate) {
		super(balance, primaryOwner, secondaryOwner, penaltyFee);
		setSecretKey(secretKey);
		setMinimumBalance(minimumBalance);
		setCreationDate(creationDate);
		setStatus(status);
		setInterestRate(interestRate);
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

	public BigDecimal getMinimumBalance() {
		return minimumBalance;
	}

	public void setMinimumBalance(BigDecimal minimumBalance) {
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
