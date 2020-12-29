package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

/**
 * TradeConstroller is in charge to hold any requests related to the
 * {@link Trade} entitie. <br>
 * In an MVC architecture, it ofcourse represent the C part and make use of the
 * {@link TradeRepository} to retrieve the data. <br>
 *
 */
@Controller
public class TradeController {

	@Autowired
	private TradeRepository tradeRepository;

	/**
	 * Retrieve all the {@link Trade} present in the database.
	 * 
	 * @param model : the list of Trade
	 * @return the list of Trade page
	 */
	@GetMapping("/trade/list")
	public String home(Model model) {
		model.addAttribute("trade", tradeRepository.findAll());
		return "trade/list";
	}

	/**
	 * GET the Trade creation template. <br>
	 * 
	 * @param trade : to create
	 * @return the template used to add a {@link Trade}
	 */
	@GetMapping("/trade/add")
	public String addTrade(Trade trade) {
		return "trade/add";
	}

	/**
	 * POST request to create a {@link Trade} and save it in the database. <br>
	 * It firstly verify that the trade created sucessfully satisfy the constraints
	 * set on the entitie. <br>
	 * 
	 * @param trade  : created
	 * @param result : verify that the created Trade is correct
	 * @param model  : the list of Trade
	 * @return redirect to the list of Trade page
	 */
	@PostMapping("/trade/validate")
	public String validate(@Valid Trade trade, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "trade/add";
		}

		tradeRepository.save(trade);
		model.addAttribute("trade", tradeRepository.findAll());
		return "redirect:/trade/list";
	}

	/**
	 * Retrieve the trade to modify from the database and then show the update
	 * template. <br>
	 * 
	 * @param id    : of the trade to modify
	 * @param model : the trade from database
	 * @return the trade update template
	 */
	@GetMapping("/trade/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Trade trade = tradeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
		model.addAttribute("trade", trade);

		return "trade/update";
	}

	/**
	 * POST request to modify a {@link Trade} and update it in the database. <br>
	 * It firstly verify that the trade modified sucessfully satisfy the constraints
	 * set on the entitie. <br>
	 * 
	 * @param id     : of the modified trade
	 * @param trade  : modified
	 * @param result : verify that the modified Trade is correct
	 * @param model  : the list of Trade
	 * @return redirect to the list of Trade page
	 */
	@PostMapping("/trade/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "trade/update";
		}

		trade.setTradeId(id);
		tradeRepository.save(trade);
		model.addAttribute("trade", tradeRepository.findAll());
		return "redirect:/trade/list";
	}

	/**
	 * Method used to delete a {@link Trade}. <br>
	 * It firstly verify that the trade is present in the database. <br>
	 * 
	 * @param id    : of the trade to delete
	 * @param model : the list of Trade
	 * @return redirect to the list of Trade page
	 */
	@GetMapping("/trade/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) {
		Trade trade = tradeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
		model.addAttribute("trade", trade);
		tradeRepository.delete(trade);
		model.addAttribute("trade", tradeRepository.findAll());
		return "redirect:/trade/list";
	}
}
