package com.ironhack.midtermproject.model.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.midtermproject.Money;
import com.ironhack.midtermproject.enums.AccountType;
import com.ironhack.midtermproject.enums.Status;
import com.ironhack.midtermproject.model.users.AccountHolder;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn(name = "accountId")
public class Savings extends Account{
	@NotNull
	private String secretKey;
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "amount", column = @Column(name = "minimum_balance")),
			@AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency"))
	})
	private Money minimumBalance;
	@NotNull
	private Status status;
	@DecimalMin(value = "0.0025", message = "Interest rate must be equal or higher than 0.0025.")
	@DecimalMax(value = "0.5", message = "Interest rate must be equal or lower than 0.5.")
	private BigDecimal interestRate;
//	@JsonIgnore
	private Date lastModificationDate;

	/*
	**	CONSTRUCTORS
	 */

	public Savings() {
	}

	public Savings(Money balance, AccountHolder primaryOwner, String secretKey, Status status) throws ParseException {
		super(balance, primaryOwner, AccountType.SAVINGS);
		setSecretKey(secretKey);
		setMinimumBalance(new Money(new BigDecimal("1000")));
		setCreationDate(new Date());
		setStatus(status);
		setInterestRate(new BigDecimal("0.0025"));


		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); //////
		Date date1 = sdf.parse("10-10-2000");////////////////////////////
		setLastModificationDate(date1);////////////////////////////////////////


//		setLastModificationDate(super.getCreationDate());
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
		this.minimumBalance = minimumBalance;
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

	public Date getLastModificationDate() {
		return lastModificationDate;
	}

	public void setLastModificationDate(Date lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}
}
