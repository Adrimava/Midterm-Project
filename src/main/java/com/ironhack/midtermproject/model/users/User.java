package com.ironhack.midtermproject.model.users;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer userId;
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
		return userId;
	}

	public void setId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
