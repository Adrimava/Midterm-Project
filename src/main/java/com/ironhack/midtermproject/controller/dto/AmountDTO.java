package com.ironhack.midtermproject.controller.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AmountDTO {
	@NotNull
	private BigDecimal amount;

	public AmountDTO() {
	}

	public AmountDTO(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
