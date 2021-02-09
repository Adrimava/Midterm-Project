package com.ironhack.midtermproyect.model.accounts;

import com.ironhack.midtermproyect.Money;
import com.ironhack.midtermproyect.enums.Status;
import com.ironhack.midtermproyect.model.users.AccountHolder;

import javax.persistence.*;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn(name = "accountId")
public class StudentChecking extends Account {
	private String secretKey;
	private Date creationDate;
	private Status status;

	/*
	**	CONSTRUCTORS
	 */

	public StudentChecking() {
	}

	public StudentChecking(Money balance, AccountHolder primaryOwner, Money penaltyFee,
						   String secretKey, Date creationDate, Status status) {
		this(balance, primaryOwner, null, penaltyFee, secretKey, creationDate, status);
	}

	public StudentChecking(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner,
						   Money penaltyFee, String secretKey, Date creationDate, Status status) {
		super(balance, primaryOwner, secondaryOwner, penaltyFee);
		this.secretKey = secretKey;
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
