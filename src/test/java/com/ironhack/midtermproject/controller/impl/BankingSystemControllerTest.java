package com.ironhack.midtermproject.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midtermproject.repository.accounts.CheckingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class BankingSystemControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private CheckingRepository checkingRepository;

	private MockMvc mockMvc;
	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void findAllChecking() {
	}

	@Test
	void findAllCreditCard() {
	}

	@Test
	void findAllSavings() {
	}

	@Test
	void findAllStudentChecking() {
	}
}