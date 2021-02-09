package com.ironhack.midtermproject.model.accounts;

import com.ironhack.midtermproject.Money;
import com.ironhack.midtermproject.enums.Status;
import com.ironhack.midtermproject.model.users.AccountHolder;

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

	public StudentChecking(Money balance, AccountHolder primaryOwner,
						   String secretKey, Status status) {
		super(balance, primaryOwner);
		setSecretKey(secretKey);
		setCreationDate(new Date());
		setStatus(status);
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
