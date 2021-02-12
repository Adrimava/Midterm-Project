package com.ironhack.midtermproject.model.users;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class ThirdParty extends User{
	private String hashedKey;

	/*
	**	CONSTRUCTORS
	 */

	public ThirdParty() {
	}

	public ThirdParty(String name, String hashedKey) {
		super(name);
		this.hashedKey = hashedKey;
		setRole("THIRDPARTY");
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
