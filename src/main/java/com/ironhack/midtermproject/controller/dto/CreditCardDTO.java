package com.ironhack.midtermproject.controller.dto;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class CreditCardDTO {
	@NotNull
	private BigDecimal money;
	@NotNull
	private Integer userId;

	public CreditCardDTO() {
	}

	public CreditCardDTO(BigDecimal money, Integer userId) {
		this.money = money;
		this.userId = userId;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
