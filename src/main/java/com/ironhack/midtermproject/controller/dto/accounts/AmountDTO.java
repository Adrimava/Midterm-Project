package com.ironhack.midtermproject.controller.dto.accounts;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AmountDTO {
	@NotNull
	@DecimalMin(value = "0.0", message = "Amount must be positive.")
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
