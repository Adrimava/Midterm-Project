package com.ironhack.midtermproyect.model.accounts;

import com.ironhack.midtermproyect.model.users.AccountHolder;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer accountId;
	protected BigDecimal balance;
	@ManyToOne
	@JoinColumn(name = "primary_owner_id")
	protected AccountHolder primaryOwner;
	@ManyToOne
	@JoinColumn(name = "secondary_owner_id")
	protected AccountHolder secondaryOwner;
	protected BigDecimal penaltyFee;

	/*
	**	CONSTRUCTORS
	 */

	public Account() {
	}

	public Account(BigDecimal balance, AccountHolder primaryOwner, BigDecimal penaltyFee) {
		this(balance, primaryOwner, null, penaltyFee);
	}

	public Account(BigDecimal balance, AccountHolder primaryOwner,
				   AccountHolder secondaryOwner, BigDecimal penaltyFee) {
		this.balance = balance;
		this.primaryOwner = primaryOwner;
		this.secondaryOwner = secondaryOwner;
		this.penaltyFee = penaltyFee;
	}

	/*
	**	GETTERS AND SETTERS
	 */

	public Integer getId() {
		return accountId;
	}

	public void setId(Integer accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public AccountHolder getPrimaryOwner() {
		return primaryOwner;
	}

	public void setPrimaryOwner(AccountHolder primaryOwner) {
		this.primaryOwner = primaryOwner;
	}

	public AccountHolder getSecondaryOwner() {
		return secondaryOwner;
	}

	public void setSecondaryOwner(AccountHolder secondaryOwner) {
		this.secondaryOwner = secondaryOwner;
	}

	public BigDecimal getPenaltyFee() {
		return penaltyFee;
	}

	public void setPenaltyFee(BigDecimal penaltyFee) {
		this.penaltyFee = penaltyFee;
	}
}
