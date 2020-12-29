package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

/**
 * RatingConstroller is in charge to hold any requests related to the
 * {@link Rating} entitie. <br>
 * In an MVC architecture, it ofcourse represent the C part and make use of the
 * {@link RatingRepository} to retrieve the data. <br>
 *
 */
@Controller
public class RatingController {

	@Autowired
	private RatingRepository ratingRepository;

	/**
	 * Retrieve all the {@link Rating} present in the database.
	 * 
	 * @param model : the list of Rating
	 * @return the list of Rating page
	 */
	@GetMapping("/rating/list")
	public String home(Model model) {
		model.addAttribute("rating", ratingRepository.findAll());
		return "rating/list";
	}

	/**
	 * GET the Rating creation template. <br>
	 * 
	 * @param rating : to create
	 * @return the template used to add a {@link Rating}
	 */
	@GetMapping("/rating/add")
	public String addRatingForm(Rating rating) {
		return "rating/add";
	}

	/**
	 * POST request to create a {@link Rating} and save it in the database. <br>
	 * It firstly verify that the rating created sucessfully satisfy the constraints
	 * set on the entitie. <br>
	 * 
	 * @param rating : created
	 * @param result : verify that the created Rating is correct
	 * @param model  : the list of Rating
	 * @return redirect to the list of Rating page
	 */
	@PostMapping("/rating/validate")
	public String validate(@Valid Rating rating, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "rating/add";
		}

		ratingRepository.save(rating);
		model.addAttribute("rating", ratingRepository.findAll());
		return "redirect:/rating/list";
	}

	/**
	 * Retrieve the rating to modify from the database and then show the update
	 * template. <br>
	 * 
	 * @param id    : of the rating to modify
	 * @param model : the rating from database
	 * @return the rating update template
	 */
	@GetMapping("/rating/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Rating rating = ratingRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
		model.addAttribute("rating", rating);
		return "rating/update";
	}

	/**
	 * POST request to modify a {@link Rating} and update it in the database. <br>
	 * It firstly verify that the rating modified sucessfully satisfy the
	 * constraints set on the entitie. <br>
	 * 
	 * @param id     : of the modified rating
	 * @param rating : modified
	 * @param result : verify that the modified Rating is correct
	 * @param model  : the list of Rating
	 * @return redirect to the list of Rating page
	 */
	@PostMapping("/rating/update/{id}")
	public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "rating/update";
		}

		rating.setId(id);
		ratingRepository.save(rating);
		model.addAttribute("rating", ratingRepository.findAll());
		return "redirect:/rating/list";
	}

	/**
	 * Method used to delete a {@link Rating}. <br>
	 * It firstly verify that the rating is present in the database. <br>
	 * 
	 * @param id    : of the rating to delete
	 * @param model : the list of Rating
	 * @return redirect to the list of Rating page
	 */
	@GetMapping("/rating/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, Model model) {
		Rating rating = ratingRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
		ratingRepository.delete(rating);
		model.addAttribute("rating", ratingRepository.findAll());
		return "redirect:/rating/list";
	}
}
