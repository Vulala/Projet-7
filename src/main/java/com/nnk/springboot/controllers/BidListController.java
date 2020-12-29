package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

/**
 * BidListConstroller is in charge to hold any requests related to the
 * {@link BidList} entitie. <br>
 * In an MVC architecture, it ofcourse represent the C part and make use of the
 * {@link BidListRepository} to retrieve the data. <br>
 *
 */
@Controller
public class BidListController {

	@Autowired
	private BidListRepository bidListRepository;

	/**
	 * Retrieve all the {@link BidList} present in the database.
	 * 
	 * @param model : the list of BidList
	 * @return the list of BidList page
	 */
	@GetMapping("/bidList/list")
	public String home(Model model) {
		model.addAttribute("bidList", bidListRepository.findAll());
		return "bidList/list";
	}

	/**
	 * GET the BidList creation template. <br>
	 * 
	 * @param bidList : to create
	 * @return the template used to add a {@link BidList}
	 */
	@GetMapping("/bidList/add")
	public String addBidForm(BidList bidList) {
		return "bidList/add";
	}

	/**
	 * POST request to create a {@link BidList} and save it in the database. <br>
	 * It firstly verify that the bidList created sucessfully satisfy the
	 * constraints set on the entitie. <br>
	 * 
	 * @param bidList : created
	 * @param result  : verify that the created BidList is correct
	 * @param model   : the list of BidList
	 * @return redirect to the list of BidList page
	 */
	@PostMapping("/bidList/validate")
	public String validate(@Valid BidList bidList, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "bidList/add";
		}

		bidListRepository.save(bidList);
		model.addAttribute("bidList", bidListRepository.findAll());
		return "redirect:/bidList/list";

	}

	/**
	 * Retrieve the bidList to modify from the database and then show the update
	 * template. <br>
	 * 
	 * @param id    : of the bidList to modify
	 * @param model : the bidList from database
	 * @return the bidList update template
	 */
	@GetMapping("/bidList/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		BidList bidList = bidListRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
		model.addAttribute("bidList", bidList);
		return "bidList/update";
	}

	/**
	 * POST request to modify a {@link BidList} and update it in the database. <br>
	 * It firstly verify that the bidList modified sucessfully satisfy the
	 * constraints set on the entitie. <br>
	 * 
	 * @param id      : of the modified bidList
	 * @param bidList : modified
	 * @param result  : verify that the modified BidList is correct
	 * @param model   : the list of BidList
	 * @return redirect to the list of BidList page
	 */
	@PostMapping("/bidList/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "bidList/update";
		}

		bidList.setBidListId(id);
		bidListRepository.save(bidList);
		model.addAttribute("bidList", bidListRepository.findAll());
		return "redirect:/bidList/list";
	}

	/**
	 * Method used to delete a {@link BidList}. <br>
	 * It firstly verify that the bidList is present in the database. <br>
	 * 
	 * @param id    : of the bidList to delete
	 * @param model : the list of BidList
	 * @return redirect to the list of BidList page
	 */
	@GetMapping("/bidList/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		BidList bidList = bidListRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
		bidListRepository.delete(bidList);
		model.addAttribute("bidList", bidListRepository.findAll());
		return "redirect:/bidList/list";
	}
}
