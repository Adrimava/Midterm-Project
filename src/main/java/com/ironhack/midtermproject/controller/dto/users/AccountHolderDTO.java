package com.ironhack.midtermproject.controller.dto.users;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ironhack.midtermproject.model.other.Address;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AccountHolderDTO {
	@NotNull
	private String name;
	@NotNull
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate birthdate;
	@NotNull
	Address address;


	public AccountHolderDTO() {
	}

	public AccountHolderDTO(String name, LocalDate birthdate, Address address) {
		this.name = name;
		this.birthdate = birthdate;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
