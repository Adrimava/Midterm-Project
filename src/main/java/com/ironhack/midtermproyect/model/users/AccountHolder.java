package com.ironhack.midtermproyect.model.users;

import com.ironhack.midtermproyect.model.other.Address;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Optional;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class AccountHolder extends User {
	private LocalDate birthDate;
	@Embedded
	private Address address;
	private Optional<String> email;

	/*
	**	CONSTRUCTORS
	 */

	public AccountHolder() {
	}

	public AccountHolder(LocalDate birthDate, Address address, Optional<String> email) {
		this.birthDate = birthDate;
		this.address = address;
		this.email = email;
	}

	public AccountHolder(String name, LocalDate birthDate, Address address, Optional<String> email) {
		super(name);
		this.birthDate = birthDate;
		this.address = address;
		this.email = email;
	}

	/*
	**	GETTERS AND SETTERS
	 */

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Optional<String> getEmail() {
		return email;
	}

	public void setEmail(Optional<String> email) {
		this.email = email;
	}
}
