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
public class HomeControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	public void injectedComponentsAreRightlySetup() {
		assertThat(mockMvc).isNotNull();
	}

	@DisplayName("GET : /")
	@Test
	public void givenGetOnHome_thenItShowTheHome() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		assertEquals(status, 200);
	}

	@DisplayName("GET : /admin/home")
	@Test
	public void givenGetOnAdminHome_thenItShowTheAdminHome() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/admin/home")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		assertEquals(status, 302);
	}
}
