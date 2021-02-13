package com.ironhack.midtermproject.model.users;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer userId;
	protected String name;
	protected String role;
	protected String password;

	/*
	**	CONSTRUCTORS
	 */

	public User() {
	}

	public User(String name) {
		this.name = name;
		this.password = "$2a$10$h9oQ5PN65O53R1btkIJu7OWPsWIvfeoZd0RaUfGOTaQJZDl306XSG"; //Password: 123456
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
