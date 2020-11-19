package com.nnk.springboot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.MyUserDetailsService;

@WithMockUser
@WebMvcTest(UserController.class)
public class UserControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private MyUserDetailsService myUserDetailsService;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	public void injectedComponentsAreRightlySetup() {
		assertThat(userRepository).isNotNull();
		assertThat(myUserDetailsService).isNotNull();
	}

	@DisplayName("GET : /user/list")
	@Test
	public void givenGetOnUserList_whenFindAll_thenItShowAllTheUsers() throws Exception {
		// ARRANGE
		List<User> listUser = new ArrayList<User>();
		when(userRepository.findAll()).thenReturn(listUser);

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/user/list")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(userRepository, times(1)).findAll();
	}

	@DisplayName("GET : /user/add")
	@Test
	public void givenGetOnUserAdd_thenItReturnTheUserAddForm() throws Exception {
		// ARRANGE
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
		User user = new User(1, "username", "password", "fullname", "role");
		when(userRepository.save(any(User.class))).thenReturn(user);

		// ACT
		MvcResult mvcResult = mockMvc
				.perform(post("/user/validate").with(csrf()).contentType(MediaType.ALL).param("username", "username")
						.param("password", "P@ssword1").param("fullname", "fullname").param("role", "role"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
		verify(userRepository, times(1)).save(any(User.class));
	}

	@DisplayName("POST not valid password:special character missing : /user/validate")
	@Test
	public void givenPostNotValidPasswordBecauseOfSpecialCharacterMissingOnUserValidate_whenSave_thenItDoesNotSaveTheUser()
			throws Exception {
		// ARRANGE
		User user = new User(1, "username", "password", "fullname", "role");
		when(userRepository.save(any(User.class))).thenReturn(user);

		// ACT
		MvcResult mvcResult = mockMvc
				.perform(post("/user/validate").with(csrf()).contentType(MediaType.ALL).param("username", "username")
						.param("password", "Password1").param("fullname", "fullname").param("role", "role"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(userRepository, times(0)).save(any(User.class));
	}

	@DisplayName("POST not valid password:uppercase missing : /user/validate")
	@Test
	public void givenPostNotValidPasswordBecauseOfUppercaseMissingOnUserValidate_whenSave_thenItDoesNotSaveTheUser()
			throws Exception {
		// ARRANGE
		User user = new User(1, "username", "password", "fullname", "role");
		when(userRepository.save(any(User.class))).thenReturn(user);

		// ACT
		MvcResult mvcResult = mockMvc
				.perform(post("/user/validate").with(csrf()).contentType(MediaType.ALL).param("username", "username")
						.param("password", "p@ssword1").param("fullname", "fullname").param("role", "role"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(userRepository, times(0)).save(any(User.class));
	}

	@DisplayName("POST not valid password:integer missing : /user/validate")
	@Test
	public void givenPostNotValidPasswordBecauseOfIntegerMissingOnUserValidate_whenSave_thenItDoesNotSaveTheUser()
			throws Exception {
		// ARRANGE
		User user = new User(1, "username", "password", "fullname", "role");
		when(userRepository.save(any(User.class))).thenReturn(user);

		// ACT
		MvcResult mvcResult = mockMvc
				.perform(post("/user/validate").with(csrf()).contentType(MediaType.ALL).param("username", "username")
						.param("password", "P@ssword").param("fullname", "fullname").param("role", "role"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(userRepository, times(0)).save(any(User.class));
	}

	@DisplayName("POST not valid password:8 characters missing : /user/validate")
	@Test
	public void givenPostNotValidPasswordBecauseOfEightCharactersMissingOnUserValidate_whenSave_thenItDoesNotSaveTheUser()
			throws Exception {
		// ARRANGE
		User user = new User(1, "username", "password", "fullname", "role");
		when(userRepository.save(any(User.class))).thenReturn(user);

		// ACT
		MvcResult mvcResult = mockMvc
				.perform(post("/user/validate").with(csrf()).contentType(MediaType.ALL).param("username", "username")
						.param("password", "P@ss1").param("fullname", "fullname").param("role", "role"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(userRepository, times(0)).save(any(User.class));
	}

	@DisplayName("POST not valid : /user/validate")
	@Test
	public void givenPostNotValidInputOnUserValidate_whenSave_thenItDoesNotSaveTheUser() throws Exception {
		// ARRANGE
		User user = new User(1, "username", "password", "fullname", "role");
		when(userRepository.save(any(User.class))).thenReturn(user);

		// ACT
		MvcResult mvcResult = mockMvc
				.perform(post("/user/validate").with(csrf()).contentType(MediaType.ALL).param("password", "pass"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(userRepository, times(0)).save(any(User.class));
	}

	@DisplayName("GET : /user/update/{id}")
	@Test
	public void givenGetOnUserUpdateId_whenFindById_thenItFindTheRightUser() throws Exception {
		// ARRANGE
		User user = new User(1, "username", "password", "fullname", "role");
		Optional<User> userOptional = Optional.of(user);
		when(userRepository.findById(any(Integer.class))).thenReturn(userOptional);

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/user/update/1")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(userRepository, times(1)).findById(1);
	}

	@DisplayName("POST : /user/update/{id}")
	@Test
	public void givenPostOnUserUpdateId_whenSave_thenItSaveTheUser() throws Exception {
		// ARRANGE
		User user = new User(1, "username", "password", "fullname", "role");
		when(userRepository.save(any(User.class))).thenReturn(user);

		// ACT
		MvcResult mvcResult = mockMvc
				.perform(post("/user/update/1").with(csrf()).contentType(MediaType.ALL).param("username", "username")
						.param("password", "P@ssword1").param("fullname", "fullname").param("role", "role"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
		verify(userRepository, times(1)).save(any(User.class));
	}

	@DisplayName("POST not valid : /user/update/{id}")
	@Test
	public void givenPostNotValidInputOnUserUpdateId_whenSave_thenItDoesNotSaveTheUser() throws Exception {
		// ARRANGE
		User user = new User(1, "username", "password", "fullname", "role");
		when(userRepository.save(any(User.class))).thenReturn(user);

		// ACT
		MvcResult mvcResult = mockMvc
				.perform(post("/user/update/1").with(csrf()).contentType(MediaType.ALL).param("password", "pass"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(userRepository, times(0)).save(any(User.class));
	}

	@DisplayName("DELETE : /user/delete/{id}")
	@Test
	public void givenDeleteOnUserDeleteId_whenDelete_thenItDeleteTheRightUser() throws Exception {
		// ARRANGE
		User user = new User(1, "username", "password", "fullname", "role");
		Optional<User> userOptional = Optional.of(user);
		when(userRepository.findById(any(Integer.class))).thenReturn(userOptional);
		Mockito.doNothing().when(userRepository).delete(any(User.class));

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/user/delete/1").with(csrf())).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
		verify(userRepository, times(1)).delete(any(User.class));
	}
}
