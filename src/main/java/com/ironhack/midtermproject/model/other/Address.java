package com.ironhack.midtermproject.model.other;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
	private String street;
	private Integer number;
}
