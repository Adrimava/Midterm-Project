package com.ironhack.midtermproject.controller.dto.accounts;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class BalanceDTO {
	@NotNull
	private BigDecimal amount;

	public BalanceDTO() {
	}

	public BalanceDTO(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
