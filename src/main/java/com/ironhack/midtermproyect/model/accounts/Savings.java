package com.ironhack.midtermproyect.model.accounts;

import com.ironhack.midtermproyect.Money;
import com.ironhack.midtermproyect.enums.Status;
import com.ironhack.midtermproyect.model.users.AccountHolder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn(name = "accountId")
public class Savings extends Account{
	private String secretKey;
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "amount", column = @Column(name = "minimum_balance")),
			@AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency"))
	})
	private Money minimumBalance;
	private Date creationDate;
	private Status status;
	private BigDecimal interestRate;

	/*
	**	CONSTRUCTORS
	 */

	public Savings() {
	}

	public Savings(Money balance, AccountHolder primaryOwner, Money penaltyFee,
				   String secretKey, Date creationDate, Status status) {
		super(balance, primaryOwner, null, penaltyFee);
		setSecretKey(secretKey);
		setMinimumBalance(new Money(new BigDecimal("1000")));
		setCreationDate(creationDate);
		setStatus(status);
		setInterestRate(new BigDecimal("0.0025"));
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

	public Money getMinimumBalance() {
		return minimumBalance;
	}

	public void setMinimumBalance(Money minimumBalance) {
		BigDecimal max = new BigDecimal("1000");
		BigDecimal min = new BigDecimal("100");
		BigDecimal amount = minimumBalance.getAmount();

		if (amount.compareTo(max) > 0 || amount.compareTo(min) < 0)
			throw new IllegalArgumentException("Minimum balance must be a value between 100 and 1000");
		else
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
		BigDecimal max = new BigDecimal("0.5");
		BigDecimal min = new BigDecimal("0.0025");

		if (interestRate.compareTo(max) > 0 || interestRate.compareTo(min) < 0)
			throw new IllegalArgumentException("Interest rate must be a value between 0.0025 and 0.5.");
		else
			this.interestRate = interestRate;
	}
}
