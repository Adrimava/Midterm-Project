package com.ironhack.midtermproyect.model.users;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Admin extends User {

	/*
	**	CONSTRUCTORS
	 */

	public Admin() {
	}

	public Admin(String name) {
		super(name);
	}
}
