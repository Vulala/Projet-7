package com.nnk.springboot.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	public void injectedComponentsAreRightlySetup() {
		assertThat(mockMvc).isNotNull();
		assertThat(userRepository).isNotNull();
	}

	@DisplayName("GET : /user/list")
	@Test
	public void givenGetOnUserList_whenFindAll_thenItShowAllTheUsers() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/user/list")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}

	@DisplayName("GET : /user/add")
	@Test
	public void givenGetOnUserAdd_thenItReturnTheUserAddForm() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/user/add")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}

	@DisplayName("POST : /user/validate")
	@Test
	public void givenPostOnUserValidate_whenSave_thenItSaveTheUser() throws Exception {
		// ARRANGE

		// ACT
		MvcResult mvcResult = mockMvc.perform(
				post("/user/validate").with(csrf()).contentType(MediaType.ALL).param("username", "usernamePost")
						.param("password", "P@ssword1").param("fullname", "fullnamePost").param("role", "rolePost"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
	}

	@DisplayName("POST not valid password:special character missing : /user/validate")
	@Test
	public void givenPostNotValidPasswordBecauseOfSpecialCharacterMissingOnUserValidate_whenSave_thenItDoesNotSaveTheUser()
			throws Exception {
		// ARRANGE
		User user = new User(999, "usernamePost", "Password1", "fullnamePost", "rolePost");

		// ACT
		MvcResult mvcResult = mockMvc.perform(
				post("/user/validate").with(csrf()).contentType(MediaType.ALL).param("username", "usernamePost")
						.param("password", "Password1").param("fullname", "fullnamePost").param("role", "rolePost"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<User> result = userRepository.findById(user.getId());

		// ASSERT
		assertEquals(status, 200);
		assertFalse(result.isPresent());
	}

	@DisplayName("POST not valid password:uppercase missing : /user/validate")
	@Test
	public void givenPostNotValidPasswordBecauseOfUppercaseMissingOnUserValidate_whenSave_thenItDoesNotSaveTheUser()
			throws Exception {
		// ARRANGE
		User user = new User(999, "usernamePost", "p@ssword1", "fullnamePost", "rolePost");

		// ACT
		MvcResult mvcResult = mockMvc.perform(
				post("/user/validate").with(csrf()).contentType(MediaType.ALL).param("username", "usernamePost")
						.param("password", "p@ssword1").param("fullname", "fullnamePost").param("role", "rolePost"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<User> result = userRepository.findById(user.getId());

		// ASSERT
		assertEquals(status, 200);
		assertFalse(result.isPresent());
	}

	@DisplayName("POST not valid password:integer missing : /user/validate")
	@Test
	public void givenPostNotValidPasswordBecauseOfIntegerMissingOnUserValidate_whenSave_thenItDoesNotSaveTheUser()
			throws Exception {
		// ARRANGE
		User user = new User(999, "usernamePost", "P@ssword", "fullnamePost", "rolePost");

		// ACT
		MvcResult mvcResult = mockMvc.perform(
				post("/user/validate").with(csrf()).contentType(MediaType.ALL).param("username", "usernamePost")
						.param("password", "P@ssword").param("fullname", "fullnamePost").param("role", "rolePost"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<User> result = userRepository.findById(user.getId());

		// ASSERT
		assertEquals(status, 200);
		assertFalse(result.isPresent());
	}

	@DisplayName("POST not valid password:8 characters missing : /user/validate")
	@Test
	public void givenPostNotValidPasswordBecauseOfEightCharactersMissingOnUserValidate_whenSave_thenItDoesNotSaveTheUser()
			throws Exception {
		// ARRANGE
		User user = new User(999, "usernamePost", "P@ss1", "fullnamePost", "rolePost");

		// ACT
		MvcResult mvcResult = mockMvc
				.perform(
						post("/user/validate").with(csrf()).contentType(MediaType.ALL).param("username", "usernamePost")
								.param("password", "P@ss1").param("fullname", "fullnamePost").param("role", "rolePost"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<User> result = userRepository.findById(user.getId());

		// ASSERT
		assertEquals(status, 200);
		assertFalse(result.isPresent());
	}

	@DisplayName("POST not valid : /user/validate")
	@Test
	public void givenPostNotValidInputOnUserValidate_whenSave_thenItDoesNotSaveTheUser() throws Exception {
		// ARRANGE
		User user = new User(999, "usernamePost", "pass", "fullnamePost", "rolePost");

		// ACT
		MvcResult mvcResult = mockMvc
				.perform(post("/user/validate").with(csrf()).contentType(MediaType.ALL).param("password", "pass"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<User> result = userRepository.findById(user.getId());

		// ASSERT
		assertEquals(status, 200);
		assertFalse(result.isPresent());
	}

	@DisplayName("GET : /user/update/{id}")
	@Test
	public void givenGetOnUserUpdateId_whenFindById_thenItFindTheRightUser() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/user/update/1")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}

	@DisplayName("POST : /user/update/{id}")
	@Test
	public void givenPostOnUserUpdateId_whenSave_thenItSaveTheUser() throws Exception {
		// ARRANGE
		User user = new User(1, "usernameUpdate", "password", "fullnameUpdate", "roleUpdate");

		// ACT
		MvcResult mvcResult = mockMvc.perform(
				post("/user/update/1").with(csrf()).contentType(MediaType.ALL).param("username", "usernameUpdate")
						.param("password", "P@ssword1").param("fullname", "fullnameUpdate").param("role", "roleUpdate"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<User> result = userRepository.findById(user.getId());

		// ASSERT
		assertEquals(status, 302);
		assertEquals(result.get().getUsername(), user.getUsername());
	}

	@DisplayName("POST not valid : /user/update/{id}")
	@Test
	public void givenPostNotValidInputOnUserUpdateId_whenSave_thenItDoesNotSaveTheUser() throws Exception {
		// ARRANGE
		User user = new User(1, "usernameUpdate", "password", "fullnameUpdate", "roleUpdate");

		// ACT
		MvcResult mvcResult = mockMvc
				.perform(post("/user/update/1").with(csrf()).contentType(MediaType.ALL).param("password", "pass"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<User> result = userRepository.findById(user.getId());

		// ASSERT
		assertEquals(status, 200);
		assertNotEquals(result.get().getUsername(), user.getUsername());
	}

	@DisplayName("DELETE : /user/delete/{id}")
	@Test
	public void givenDeleteOnUserDeleteId_whenDelete_thenItDeleteTheRightUser() throws Exception {
		// ARRANGE
		User user = new User(1, "username", "password", "fullname", "role");

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/user/delete/1").with(csrf())).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<User> result = userRepository.findById(user.getId());

		// ASSERT
		assertEquals(status, 302);
		assertFalse(result.isPresent());
	}
}
