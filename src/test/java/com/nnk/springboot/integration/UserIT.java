package com.nnk.springboot.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserIT {

	@Autowired
	private UserRepository userRepository;

	private User user;

	@DisplayName("Initialize a user")
	@BeforeEach
	public void initialization() {
		user = new User(1, "Username", "Password", "Fullname", "Role");
	}

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	public void injectedComponentsAreRightlySetup() {
		assertThat(userRepository).isNotNull();
	}

	@DisplayName("Find by id : user")
	@Test
	public void givenGettingAnUser_whenFindById_thenItReturnTheRightUser() {
		Optional<User> result = userRepository.findById(user.getId());
		assertTrue(result.isPresent());
	}

	@DisplayName("Find all : user")
	@Test
	public void givenGettingAllUser_whenFindAll_thenItReturnAllTheUsers() {
		List<User> listResult = userRepository.findAll();
		assertTrue(listResult.size() > 0);
	}

	@DisplayName("Save : user")
	@Test
	public void givenSavingAnUser_whenSave_thenItSaveTheUserInTheDB() {
		user = userRepository.save(user);
		assertNotNull(userRepository.findById(user.getId()));
		assertEquals(userRepository.findById(user.getId()).get().getUsername(), "Username");
	}

	@DisplayName("Update : user")
	@Test
	public void givenUpdatingAnUser_whenUpdate_thenItUpdateTheUserInTheDB() {
		user.setUsername("Username Updated");
		user = userRepository.save(user);
		assertNotNull(userRepository.findById(user.getId()));
		assertEquals(userRepository.findById(user.getId()).get().getUsername(), "Username Updated");
	}

	@DisplayName("Delete : user")
	@Test
	public void givenDeletingAnUser_whenDelete_thenItDeleteTheUserInTheDB() {
		userRepository.delete(user);
		assertFalse(userRepository.findById(user.getId()).isPresent());
	}

}
