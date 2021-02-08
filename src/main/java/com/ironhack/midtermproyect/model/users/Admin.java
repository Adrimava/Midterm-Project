package com.ironhack.midtermproyect.model.users;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "id")
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
