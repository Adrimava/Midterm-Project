package com.ironhack.midtermproject.model.accounts;

import com.ironhack.midtermproject.Money;
import com.ironhack.midtermproject.enums.AccountType;
import com.ironhack.midtermproject.enums.Status;
import com.ironhack.midtermproject.model.users.AccountHolder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn(name = "accountId")
public class Checking extends Account{
	@NotNull
	private String secretKey;
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "amount", column = @Column(name = "minimum_balance")),
			@AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency"))
	})
	private Money minimumBalance;
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "amount", column = @Column(name = "monthly_maintenance_fee")),
			@AttributeOverride(name = "currency", column = @Column(name = "monthly_maintenance_fee_currency"))
	})
	private Money monthlyMaintenanceFee;
	@NotNull
	private Status status;

	/*
	**	CONSTRUCTORS
	 */

	public Checking() {
	}

	public Checking(Money balance, AccountHolder primaryOwner,
					String secretKey, Status status) {
		super(balance, primaryOwner, AccountType.CHECKING);
		setSecretKey(secretKey);
		setMinimumBalance(new Money(new BigDecimal("250")));
		setMonthlyMaintenanceFee(new Money(new BigDecimal("12")));
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
