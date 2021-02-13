package com.ironhack.midtermproject.controller.dto.users;

import javax.validation.constraints.NotNull;

public class AdminDTO {
	@NotNull
	private String name;

	public AdminDTO() {
	}

	public AdminDTO(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
