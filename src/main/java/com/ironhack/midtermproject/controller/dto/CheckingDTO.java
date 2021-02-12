package com.ironhack.midtermproject.controller.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CheckingDTO {
	@NotNull
	private BigDecimal balance;
	@NotNull
	private Integer userId;
	@NotNull
	private String secretKey;
	@NotNull
	private String status;

	public CheckingDTO() {
	}

	public CheckingDTO(BigDecimal balance, Integer userId, String secretKey, String status) {
		this.balance = balance;
		this.userId = userId;
		this.secretKey = secretKey;
		this.status = status;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
