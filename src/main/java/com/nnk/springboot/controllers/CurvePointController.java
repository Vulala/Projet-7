package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

/**
 * CurvePointController is in charge to hold any requests related to the
 * {@link CurvePoint} entitie. <br>
 * In an MVC architecture, it ofcourse represent the C part and make use of the
 * {@link CurvePointRepository} to retrieve the data. <br>
 *
 */
@Controller
public class CurvePointController {

	@Autowired
	private CurvePointRepository curvePointRepository;

	/**
	 * Retrieve all the {@link CurvePoint} present in the database.
	 * 
	 * @param model : the list of CurvePoint
	 * @return the list of CurvePoint page
	 */
	@GetMapping("/curvePoint/list")
	public String home(Model model) {
		model.addAttribute("curvePoint", curvePointRepository.findAll());
		return "curvePoint/list";
	}

	/**
	 * GET the CurvePoint creation template. <br>
	 * 
	 * @param curvePoint : to create
	 * @return the template used to add a {@link CurvePoint}
	 */
	@GetMapping("/curvePoint/add")
	public String addBidForm(CurvePoint curvePoint) {
		return "curvePoint/add";
	}

	/**
	 * POST request to create a {@link CurvePoint} and save it in the database. <br>
	 * It firstly verify that the curvePoint created sucessfully satisfy the
	 * constraints set on the entitie. <br>
	 * 
	 * @param curvePoint : created
	 * @param result     : verify that the created CurvePoint is correct
	 * @param model      : the list of CurvePoint
	 * @return redirect to the list of CurvePoint page
	 */
	@PostMapping("/curvePoint/validate")
	public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "curvePoint/add";
		}

		curvePointRepository.save(curvePoint);
		model.addAttribute("curvePoint", curvePointRepository.findAll());
		return "redirect:/curvePoint/list";

	}

	/**
	 * Retrieve the curvePoint to modify from the database and then show the update
	 * template. <br>
	 * 
	 * @param id    : of the curvePoint to modify
	 * @param model : the curvePoint from database
	 * @return the curvePoint update template
	 */
	@GetMapping("/curvePoint/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		CurvePoint curvePoint = curvePointRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
		model.addAttribute("curvePoint", curvePoint);
		return "curvePoint/update";
	}

	/**
	 * POST request to modify a {@link CurvePoint} and update it in the database.
	 * <br>
	 * It firstly verify that the curvePoint modified sucessfully satisfy the
	 * constraints set on the entitie. <br>
	 * 
	 * @param id         : of the modified curvePoint
	 * @param curvePoint : modified
	 * @param result     : verify that the modified CurvePoint is correct
	 * @param model      : the list of CurvePoint
	 * @return redirect to the list of CurvePoint page
	 */
	@PostMapping("/curvePoint/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "curvePoint/update";
		}

		curvePoint.setId(id);
		curvePointRepository.save(curvePoint);
		model.addAttribute("curvePoint", curvePointRepository.findAll());
		return "redirect:/curvePoint/list";
	}

	/**
	 * Method used to delete a {@link CurvePoint}. <br>
	 * It firstly verify that the curvePoint is present in the database. <br>
	 * 
	 * @param id    : of the curvePoint to delete
	 * @param model : the list of CurvePoint
	 * @return redirect to the list of CurvePoint page
	 */
	@GetMapping("/curvePoint/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		CurvePoint curvePoint = curvePointRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
		curvePointRepository.delete(curvePoint);
		model.addAttribute("curvePoint", curvePointRepository.findAll());
		return "redirect:/curvePoint/list";
	}
}
