package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

/**
 * UserConstroller is in charge to hold any requests related to the {@link User}
 * entitie. <br>
 * In an MVC architecture, it ofcourse represent the C part and make use of the
 * {@link UserRepository} to retrieve the data. <br>
 *
 */
@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Retrieve all the {@link User} present in the database.
	 * 
	 * @param model : the list of User
	 * @return the list of User page
	 */
	@GetMapping("/user/list")
	public String home(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "user/list";
	}

	/**
	 * GET the User creation template. <br>
	 * 
	 * @param user : to create
	 * @return the template used to add a {@link User}
	 */
	@GetMapping("/user/add")
	public String addUser(User user) {
		return "user/add";
	}

	/**
	 * POST request to create a {@link User} and save it in the database. <br>
	 * It firstly verify that the user created sucessfully satisfy the constraints
	 * set on the entitie. <br>
	 * Then it encode the password before saving it for it to be crypted. <br>
	 * 
	 * @param user   : created
	 * @param result : verify that the created User is correct
	 * @param model  : the list of User
	 * @return redirect to the list of User page
	 */
	@PostMapping("/user/validate")
	public String validate(@Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "user/add";
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		model.addAttribute("users", userRepository.findAll());

		return "redirect:/user/list";
	}

	/**
	 * Retrieve the user to modify from the database and then show the update
	 * template. <br>
	 * 
	 * @param id    : of the user to modify
	 * @param model : the user from database
	 * @return the user update template
	 */
	@GetMapping("/user/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		user.setPassword("");
		model.addAttribute("user", user);
		return "user/update";
	}

	/**
	 * POST request to modify a {@link User} and update it in the database. <br>
	 * It firstly verify that the user modified sucessfully satisfy the constraints
	 * set on the entitie. <br>
	 * Then it encode the password before saving it for it to be crypted. <br>
	 * 
	 * @param id     : of the modified user
	 * @param user   : modified
	 * @param result : verify that the modified User is correct
	 * @param model  : the list of User
	 * @return redirect to the list of User page
	 */
	@PostMapping("/user/update/{id}")
	public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "user/update";
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setId(id);
		userRepository.save(user);
		model.addAttribute("users", userRepository.findAll());
		return "redirect:/user/list";

	}

	/**
	 * Method used to delete a {@link User}. <br>
	 * It firstly verify that the user is present in the database. <br>
	 * 
	 * @param id    : of the user to delete
	 * @param model : the list of User
	 * @return redirect to the list of User page
	 */
	@GetMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userRepository.delete(user);
		model.addAttribute("users", userRepository.findAll());
		return "redirect:/user/list";
	}
}
