package com.ironhack.midtermproyect.model.users;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class ThirdParty extends User{
	private String hashedKey;

	/*
	**	CONSTRUCTORS
	 */

	public ThirdParty() {
	}

	public ThirdParty(String hashedKey) {
		this.hashedKey = hashedKey;
	}

	public ThirdParty(String name, String hashedKey) {
		super(name);
		this.hashedKey = hashedKey;
	}

	/*
	**	GETTERS AND SETTERS
	 */

	public String getHashedKey() {
		return hashedKey;
	}

	public void setHashedKey(String hashedKey) {
		this.hashedKey = hashedKey;
	}
}
