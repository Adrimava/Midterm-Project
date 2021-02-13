package com.ironhack.midtermproject.controller.dto.accounts;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AccountDTO {
	@NotNull
	private BigDecimal amount;
	@NotNull
	private Integer accountId;
	@NotNull
	private String accountSecretKey;

	public AccountDTO() {
	}

	public AccountDTO(BigDecimal amount, Integer accountId, String accountSecretKey) {
		this.amount = amount;
		this.accountId = accountId;
		this.accountSecretKey = accountSecretKey;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getAccountSecretKey() {
		return accountSecretKey;
	}

	public void setAccountSecretKey(String accountSecretKey) {
		this.accountSecretKey = accountSecretKey;
	}
}
