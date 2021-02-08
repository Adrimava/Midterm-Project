package com.ironhack.midtermproyect.model.accounts;

import com.ironhack.midtermproyect.Money;
import com.ironhack.midtermproyect.model.users.AccountHolder;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer accountId;
	protected Money balance;
	@ManyToOne
	@JoinColumn(name = "primary_owner_id")
	protected AccountHolder primaryOwner;
	@ManyToOne
	@JoinColumn(name = "secondary_owner_id")
	protected AccountHolder secondaryOwner;
	protected Money penaltyFee;

	/*
	**	CONSTRUCTORS
	 */

	public Account() {
	}

	public Account(Money balance, AccountHolder primaryOwner, Money penaltyFee) {
		this(balance, primaryOwner, null, penaltyFee);
	}

	public Account(Money balance, AccountHolder primaryOwner,
				   AccountHolder secondaryOwner, Money penaltyFee) {
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

	public Money getBalance() {
		return balance;
	}

	public void setBalance(Money balance) {
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

	public Money getPenaltyFee() {
		return penaltyFee;
	}

	public void setPenaltyFee(Money penaltyFee) {
		this.penaltyFee = penaltyFee;
	}
}
