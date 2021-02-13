package com.ironhack.midtermproject.model.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.midtermproject.model.other.Money;
import com.ironhack.midtermproject.enums.AccountType;
import com.ironhack.midtermproject.model.users.AccountHolder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer accountId;
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "amount", column = @Column(name = "balance")),
			@AttributeOverride(name = "currency", column = @Column(name = "balance_currency"))
	})
	protected Money balance;
	@ManyToOne
	@JoinColumn(name = "primary_owner_id")
	protected AccountHolder primaryOwner;
	@ManyToOne
	@JoinColumn(name = "secondary_owner_id")
	protected AccountHolder secondaryOwner;
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "amount", column = @Column(name = "penalty_fee")),
			@AttributeOverride(name = "currency", column = @Column(name = "penalty_fee_currency"))
	})
	protected Money penaltyFee;
	@JsonIgnore
	protected AccountType accountType;
	protected Date creationDate;
	protected String secretKey;

	/*
	**	CONSTRUCTORS
	 */

	public Account() {
	}

	public Account(Money balance, AccountHolder primaryOwner, AccountType accountType) {
		setBalance(balance);
		setPrimaryOwner(primaryOwner);
		setSecondaryOwner(null);
		setPenaltyFee(new Money(new BigDecimal("40")));
		setAccountType(accountType);
		setCreationDate(new Date());
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

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
}
