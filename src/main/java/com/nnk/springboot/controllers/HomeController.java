package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller used to show the first page of the application. <br>
 */
@Controller
public class HomeController {

	/**
	 * GET the index. <br>
	 * 
	 * @param model
	 * @return the home page
	 */
	@GetMapping("/")
	public String home(Model model) {
		return "home";
	}

	/**
	 * GET the list of BidList page. <br>
	 * 
	 * @param model
	 * @return the list of BidList page
	 */
	@GetMapping("/admin/home")
	public String adminHome(Model model) {
		return "redirect:/bidList/list";
	}

}
