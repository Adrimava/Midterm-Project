package com.ironhack.midtermproject.model.accounts;

import com.ironhack.midtermproject.Money;
import com.ironhack.midtermproject.enums.AccountType;
import com.ironhack.midtermproject.enums.Status;
import com.ironhack.midtermproject.model.users.AccountHolder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn(name = "accountId")
public class StudentChecking extends Account {
	@NotNull
	private Status status;

	/*
	**	CONSTRUCTORS
	 */

	public StudentChecking() {
	}

	public StudentChecking(Money balance, AccountHolder primaryOwner,
						   String secretKey, Status status) {
		super(balance, primaryOwner, AccountType.STUDENT_CHECKING);
		setSecretKey(secretKey);
		setStatus(status);
	}

	/*
	**	GETTERS AND SETTERS
	 */

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
