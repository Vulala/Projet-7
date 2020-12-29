package com.nnk.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nnk.springboot.repositories.UserRepository;

/**
 * Controller used for the login part of the application. <br>
 * It also set an error mapping and an admin specific endpoint. <br>
 */
@Controller
public class LoginController {

	@Autowired
	private UserRepository userRepository;

	/**
	 * GET request to get the login page. <br>
	 * 
	 * @return the login template
	 */
	@GetMapping("login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		return mav;
	}

	/**
	 * Retrieve all the user from the database. <br>
	 * 
	 * @return the list of the user present in the database
	 */
	@GetMapping("secure/article-details")
	public ModelAndView getAllUserArticles() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("users", userRepository.findAll());
		mav.setViewName("user/list");
		return mav;
	}

	/**
	 * Map the error endpoint. <br>
	 * 
	 * @return the error 403 template
	 */
	@GetMapping("error")
	public ModelAndView error() {
		ModelAndView mav = new ModelAndView();
		String errorMessage = "You are not authorized for the requested data.";
		mav.addObject("errorMsg", errorMessage);
		mav.setViewName("403");
		return mav;
	}
}
