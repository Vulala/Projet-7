package com.nnk.springboot.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LoginControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	public void injectedComponentsAreRightlySetup() {
		assertThat(mockMvc).isNotNull();
	}

	@DisplayName("GET : /login")
	@Test
	public void givenGetOnLogin_thenItShowTheLoginPage() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/login")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		assertEquals(status, 200);
	}

	@DisplayName("GET : /secure/article-details")
	@Test
	public void givenGetOnSecureArticledetails_thenItShowTheUserList() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/secure/article-details")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		assertEquals(status, 302);
	}

	@DisplayName("GET : /error")
	@Test
	public void givenGetOnError_thenItShowTheErrorPage() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/error")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		assertEquals(status, 200);
	}
}
