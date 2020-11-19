package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.nnk.springboot.controllers.LoginController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.MyUserDetailsService;

@WebMvcTest(LoginController.class)
public class LoginControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private MyUserDetailsService myUserDetailsService;

	@DisplayName("GET : /login")
	@Test
	public void givenGetLogin_thenItReturnTheLogin() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/login")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		assertEquals(status, 200);
	}

	@DisplayName("GET : /secure/article-details")
	@Test
	public void givenGetSecureArticledetails_thenItReturnTheArticledetails() throws Exception {
		List<User> listUser = new ArrayList<User>();
		when(userRepository.findAll()).thenReturn(listUser);

		MvcResult mvcResult = mockMvc.perform(get("/secure/article-details")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		assertEquals(status, 302);
	}

	@DisplayName("GET : /error")
	@Test
	public void givenGetError_thenItReturnTheErrorTemplate() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/error")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		assertEquals(status, 500);
	}
}
