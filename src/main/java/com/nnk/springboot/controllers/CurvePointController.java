package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@Controller
public class CurvePointController {

	@Autowired
	private CurvePointRepository curvePointRepository;

	@RequestMapping("/curvePoint/list")
	public String home(Model model) {
		model.addAttribute("curvePoint", curvePointRepository.findAll());
		return "curvePoint/list";
	}

	@GetMapping("/curvePoint/add")
	public String addBidForm(CurvePoint curvePoint) {
		return "curvePoint/add";
	}

	@PostMapping("/curvePoint/validate")
	public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "curvePoint/add";
		}

		curvePointRepository.save(curvePoint);
		model.addAttribute("curvePoint", curvePointRepository.findAll());
		return "redirect:/curvePoint/list";

	}

	@GetMapping("/curvePoint/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		CurvePoint curvePoint = curvePointRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
		model.addAttribute("curvePoint", curvePoint);
		return "curvePoint/update";
	}

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

	@GetMapping("/curvePoint/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		CurvePoint curvePoint = curvePointRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
		curvePointRepository.delete(curvePoint);
		model.addAttribute("curvePoint", curvePointRepository.findAll());
		return "redirect:/curvePoint/list";
	}
}
