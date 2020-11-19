package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.nnk.springboot.controllers.HomeController;
import com.nnk.springboot.services.MyUserDetailsService;

@WebMvcTest(HomeController.class)
public class HomeControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MyUserDetailsService myUserDetailsService;

	@DisplayName("GET : /")
	@Test
	public void givenGetHome_thenItReturnTheHome() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		assertEquals(status, 200);
	}

	@DisplayName("GET : /admin/home")
	@Test
	public void givenGetAdminHome_thenItReturnTheAdminHome() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/admin/home")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		assertEquals(status, 302);
	}
}
