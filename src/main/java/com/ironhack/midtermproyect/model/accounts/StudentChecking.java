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

	public StudentChecking(Money balance, AccountHolder primaryOwner,
						   String secretKey, Status status) {
		super(balance, primaryOwner, null);
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
