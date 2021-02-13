package com.ironhack.midtermproject.model.other;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer transactionId;
	private Integer userId;
	private Integer accountId;
	private Money money;
	private Long date;
	@JsonIgnore
	private static Integer highestDailyTotalTransactions;

	public Transaction() {
	}

	public Transaction(Integer userId, Integer accountId, Money money) {
		setUserId(userId);
		setAccountId(accountId);
		setMoney(money);
		setDate(new Date().getTime());
		setHighestDailyTotalTransactions();
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Money getMoney() {
		return money;
	}

	public void setMoney(Money money) {
		this.money = money;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public static Integer getHighestDailyTotalTransactions() {
		return highestDailyTotalTransactions;
	}

	public static void setHighestDailyTotalTransactions(Integer highestDailyTotalTransactions) {
		Transaction.highestDailyTotalTransactions = highestDailyTotalTransactions;
	}

	public static void setHighestDailyTotalTransactions() {
		if (Transaction.highestDailyTotalTransactions == null) {
			Transaction.highestDailyTotalTransactions = 100;
		}
	}
}
