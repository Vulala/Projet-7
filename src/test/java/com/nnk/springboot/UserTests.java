package com.nnk.springboot;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void userTest() {
		User user = new User("Username", "Password", "Full Name", "Role");

		// Save
		user = userRepository.save(user);
		assertNotNull(user.getId());
		assertTrue(user.getUsername().equals("Username"));

		// Update
		user.setUsername("Username Update");
		user = userRepository.save(user);
		assertTrue(user.getUsername().equals("Username Update"));

		// Find
		List<User> listResult = userRepository.findAll();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = user.getId();
		userRepository.delete(user);
		Optional<User> userList = userRepository.findById(id);
		assertFalse(userList.isPresent());
	}
}
