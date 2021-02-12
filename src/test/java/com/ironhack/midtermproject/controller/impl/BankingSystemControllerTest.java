package com.ironhack.midtermproject.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midtermproject.Money;
import com.ironhack.midtermproject.controller.dto.AmountDTO;
import com.ironhack.midtermproject.controller.dto.CheckingDTO;
import com.ironhack.midtermproject.controller.dto.CreditCardDTO;
import com.ironhack.midtermproject.controller.dto.SavingsDTO;
import com.ironhack.midtermproject.enums.Status;
import com.ironhack.midtermproject.model.accounts.Checking;
import com.ironhack.midtermproject.model.accounts.CreditCard;
import com.ironhack.midtermproject.model.accounts.Savings;
import com.ironhack.midtermproject.model.accounts.StudentChecking;
import com.ironhack.midtermproject.model.other.Address;
import com.ironhack.midtermproject.model.users.AccountHolder;
import com.ironhack.midtermproject.repository.accounts.CheckingRepository;
import com.ironhack.midtermproject.repository.accounts.CreditCardRepository;
import com.ironhack.midtermproject.repository.accounts.SavingsRepository;
import com.ironhack.midtermproject.repository.accounts.StudentCheckingRepository;
import com.ironhack.midtermproject.repository.users.AccountHolderRepository;
import com.ironhack.midtermproject.repository.users.AdminRepository;
import com.ironhack.midtermproject.repository.users.ThirdPartyRepository;
import com.ironhack.midtermproject.service.interfaces.IBankingSystemService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BankingSystemControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private CheckingRepository checkingRepository;
	@Autowired
	private CreditCardRepository creditCardRepository;
	@Autowired
	private SavingsRepository savingsRepository;
	@Autowired
	private StudentCheckingRepository studentCheckingRepository;
	@Autowired
	private AccountHolderRepository accountHolderRepository;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private ThirdPartyRepository thirdPartyRepository;
	@Autowired
	private IBankingSystemService bankingSystemService;

	private MockMvc mockMvc;
	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		Money money1 = new Money(new BigDecimal("5555"));
		Money money2 = new Money(new BigDecimal("7777"));
		Address address1 = new Address("Gran Via", 90);
		Address address2 = new Address("Avda Constitucion", 10);
		LocalDate localDate1 = LocalDate.of(1950, 10, 10);
		LocalDate localDate2 = LocalDate.of(2000, 5, 5);
		AccountHolder accountHolder1 = new AccountHolder("Jose Perez", localDate1, address1);
		AccountHolder accountHolder2 = new AccountHolder("Manuela Garcia", localDate2, address2);
		Checking checking1 = new Checking(money1, accountHolder1, "secretKey", Status.ACTIVE);
		Checking checking2 = new Checking(money2, accountHolder2, "password", Status.FROZEN);
		CreditCard creditCard1 = new CreditCard(money1, accountHolder1);
		CreditCard creditCard2 = new CreditCard(money2, accountHolder2);
		Savings savings1 = new Savings(money1, accountHolder1, "SavingsKey", Status.ACTIVE);
		Savings savings2 = new Savings(money2, accountHolder2, "SavingsPassword", Status.FROZEN);
		StudentChecking studentChecking1 = new StudentChecking(money1, accountHolder1, "StudentKey", Status.FROZEN);
		StudentChecking studentChecking2 = new StudentChecking(money2, accountHolder2, "StudentPassword", Status.ACTIVE);

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		accountHolderRepository.saveAll(List.of(accountHolder1, accountHolder2));
		checkingRepository.saveAll(List.of(checking1, checking2));
		creditCardRepository.saveAll(List.of(creditCard1, creditCard2));
		savingsRepository.saveAll(List.of(savings1, savings2));
		studentCheckingRepository.saveAll(List.of(studentChecking1, studentChecking2));
	}

	@AfterEach
	void tearDown() {
		studentCheckingRepository.deleteAll();
		savingsRepository.deleteAll();
		creditCardRepository.deleteAll();
		checkingRepository.deleteAll();
		accountHolderRepository.deleteAll();
	}


	@Test
	void findAllChecking_NoParam_AllChecking() throws Exception {
		MvcResult result = mockMvc.perform(get("/checking")).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("secretKey"));
		assertTrue(result.getResponse().getContentAsString().contains("password"));
	}

	@Test
	void findAllCreditCard_NoParam_AllCreditCard() throws Exception {
		MvcResult result = mockMvc.perform(get("/credit-card")).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("Jose"));
		assertTrue(result.getResponse().getContentAsString().contains("Manuela"));
	}

	@Test
	void findAllSavings_NoParam_AllSavings() throws Exception {
		MvcResult result = mockMvc.perform(get("/savings")).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("SavingsKey"));
		assertTrue(result.getResponse().getContentAsString().contains("SavingsPassword"));
	}

	@Test
	void findAllStudentChecking_NoParam_AllStudentChecking() throws Exception {
		MvcResult result = mockMvc.perform(get("/student-checking")).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("StudentKey"));
		assertTrue(result.getResponse().getContentAsString().contains("StudentPassword"));
	}

	@Test
	void findCheckingById_ValidId_Checking() throws Exception {
		MvcResult result = mockMvc.perform(get("/checking/1")).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("secretKey"));
	}

	@Test
	void findCreditCardById_ValidId_CreditCard() throws Exception {
		MvcResult result = mockMvc.perform(get("/credit-card/3")).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("Jose"));
	}

	@Test
	void findSavingsById_ValidId_Savings() throws Exception {
		MvcResult result = mockMvc.perform(get("/savings/5")).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("SavingsKey"));
	}

	@Test
	void findStudentCheckingById_ValidId_StudentChecking() throws Exception {
		MvcResult result = mockMvc.perform(get("/student-checking/7")).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("StudentKey"));
	}

	@Test
	void createChecking_ValidId_Checking() throws Exception {
		CheckingDTO checkingDTO = new CheckingDTO(new BigDecimal("1000"), 1, "patata", "active");

		String body = objectMapper.writeValueAsString(checkingDTO);
		MvcResult result = mockMvc.perform(
				post("/checking")
						.content(body)
						.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isCreated()).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("patata"));
	}

	@Test
	void createCreditCard_ValidId_CreditCard() throws Exception{
		CreditCardDTO creditCardDTO = new CreditCardDTO(new BigDecimal("1100"), 1);

		String body = objectMapper.writeValueAsString(creditCardDTO);
		MvcResult result = mockMvc.perform(
				post("/credit-card")
						.content(body)
						.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isCreated()).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("1100"));
	}

	@Test
	void createSavings_ValidId_Savings() throws Exception {
		SavingsDTO savingsDTO = new SavingsDTO(new BigDecimal("1000"), 1, "tomato", "active");

		String body = objectMapper.writeValueAsString(savingsDTO);
		MvcResult result = mockMvc.perform(
				post("/savings")
						.content(body)
						.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isCreated()).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("tomato"));
	}

	@Test
	void withdraw_ValidUser_ValidAcount() throws Exception {
		AmountDTO amountDTO = new AmountDTO(new BigDecimal("555"));

		String body = objectMapper.writeValueAsString(amountDTO);
		MvcResult result = mockMvc.perform(
				patch("/withdraw/1/1")
						.content(body)
						.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isNoContent()).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("5000"));
	}

	@Test
	void deposit_ValidUser_ValidAcount() throws Exception {
		AmountDTO amountDTO = new AmountDTO(new BigDecimal("100"));

		String body = objectMapper.writeValueAsString(amountDTO);
		MvcResult result = mockMvc.perform(
				patch("/deposit/1/1")
						.content(body)
						.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isNoContent()).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("5100"));
	}

	@Test
	void transaction_ValidUser_ValidAcount() throws Exception {
		AmountDTO amountDTO = new AmountDTO(new BigDecimal("100"));

		String body = objectMapper.writeValueAsString(amountDTO);
		MvcResult result = mockMvc.perform(
				patch("/deposit/1/1/2")
						.content(body)
						.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isNoContent()).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("5000"));
	}

}