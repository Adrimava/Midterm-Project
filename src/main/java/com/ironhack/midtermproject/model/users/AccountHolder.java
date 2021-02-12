package com.ironhack.midtermproject.model.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.midtermproject.model.accounts.Account;
import com.ironhack.midtermproject.model.other.Address;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class AccountHolder extends User {
	private LocalDate birthDate;
	@Embedded
	private Address address;
	private String email;
	@JsonIgnore
	@OneToMany(mappedBy = "primaryOwner")
	private List<Account> primaryAccounts;
	@JsonIgnore
	@OneToMany(mappedBy = "secondaryOwner")
	private List<Account> secondaryAccounts;

	/*
	**	CONSTRUCTORS
	 */

	public AccountHolder() {
	}

	public AccountHolder(String name, LocalDate birthDate, Address address) {
		this(name, birthDate, address, null);
	}

	public AccountHolder(String name, LocalDate birthDate, Address address, String email) {
		super(name);
		setBirthDate(birthDate);
		setAddress(address);
		setEmail(email);
		setRole("ACCOUNTHOLDER");
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Account> getPrimaryAccounts() {
		return primaryAccounts;
	}

	public void setPrimaryAccounts(List<Account> primaryAccounts) {
		this.primaryAccounts = primaryAccounts;
	}

	public List<Account> getSecondaryAccounts() {
		return secondaryAccounts;
	}

	public void setSecondaryAccounts(List<Account> secondaryAccounts) {
		this.secondaryAccounts = secondaryAccounts;
	}
}
