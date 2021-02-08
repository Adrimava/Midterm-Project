package com.ironhack.midtermproyect.model.users;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	protected String name;

	/*
	**	CONSTRUCTORS
	 */

	public User() {
	}

	public User(String name) {
		this.name = name;
	}

	/*
	**	GETTERS AND SETTERS
	 */

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
