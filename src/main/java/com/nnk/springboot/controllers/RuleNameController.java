package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

/**
 * RuleNameConstroller is in charge to hold any requests related to the
 * {@link RuleName} entitie. <br>
 * In an MVC architecture, it ofcourse represent the C part and make use of the
 * {@link RuleNameRepository} to retrieve the data. <br>
 *
 */
@Controller
public class RuleNameController {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	/**
	 * Retrieve all the {@link RuleName} present in the database.
	 * 
	 * @param model : the list of RuleName
	 * @return the list of RuleName page
	 */
	@GetMapping("/ruleName/list")
	public String home(Model model) {
		model.addAttribute("ruleName", ruleNameRepository.findAll());
		return "ruleName/list";
	}

	/**
	 * GET the RuleName creation template. <br>
	 * 
	 * @param ruleName : to create
	 * @return the template used to add a {@link RuleName}
	 */
	@GetMapping("/ruleName/add")
	public String addRuleForm(RuleName ruleName) {
		return "ruleName/add";
	}

	/**
	 * POST request to create a {@link RuleName} and save it in the database. <br>
	 * It firstly verify that the ruleName created sucessfully satisfy the
	 * constraints set on the entitie. <br>
	 * 
	 * @param ruleName : created
	 * @param result   : verify that the created RuleName is correct
	 * @param model    : the list of RuleName
	 * @return redirect to the list of RuleName page
	 */
	@PostMapping("/ruleName/validate")
	public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "ruleName/add";
		}

		ruleNameRepository.save(ruleName);
		model.addAttribute("ruleName", ruleNameRepository.findAll());
		return "redirect:/ruleName/list";

	}

	/**
	 * Retrieve the ruleName to modify from the database and then show the update
	 * template. <br>
	 * 
	 * @param id    : of the ruleName to modify
	 * @param model : the ruleName from database
	 * @return the ruleName update template
	 */
	@GetMapping("/ruleName/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		RuleName ruleName = ruleNameRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
		model.addAttribute("ruleName", ruleName);
		return "ruleName/update";
	}

	/**
	 * POST request to modify a {@link RuleName} and update it in the database. <br>
	 * It firstly verify that the ruleName modified sucessfully satisfy the
	 * constraints set on the entitie. <br>
	 * 
	 * @param id       : of the modified ruleName
	 * @param ruleName : modified
	 * @param result   : verify that the modified RuleName is correct
	 * @param model    : the list of RuleName
	 * @return redirect to the list of RuleName page
	 */
	@PostMapping("/ruleName/update/{id}")
	public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "ruleName/update";
		}

		ruleName.setId(id);
		ruleNameRepository.save(ruleName);
		model.addAttribute("ruleName", ruleNameRepository.findAll());
		return "redirect:/ruleName/list";
	}

	/**
	 * Method used to delete a {@link RuleName}. <br>
	 * It firstly verify that the ruleName is present in the database. <br>
	 * 
	 * @param id    : of the ruleName to delete
	 * @param model : the list of RuleName
	 * @return redirect to the list of RuleName page
	 */
	@GetMapping("/ruleName/delete/{id}")
	public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
		RuleName ruleName = ruleNameRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
		ruleNameRepository.delete(ruleName);
		model.addAttribute("ruleName", ruleNameRepository.findAll());
		return "redirect:/ruleName/list";
	}
}
