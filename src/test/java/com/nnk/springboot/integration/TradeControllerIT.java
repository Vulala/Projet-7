package com.nnk.springboot.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TradeControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TradeRepository tradeRepository;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	public void injectedComponentsAreRightlySetup() {
		assertThat(mockMvc).isNotNull();
		assertThat(tradeRepository).isNotNull();
	}

	@DisplayName("GET : /trade/list")
	@Test
	public void givenGetOnTradeList_whenFindAll_thenItShowAllTheTrades() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/trade/list")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}

	@DisplayName("GET : /trade/add")
	@Test
	public void givenGetOnTradeAdd_thenItReturnTheTradeAddForm() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/trade/add")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}

	@DisplayName("POST : /trade/validate")
	@Test
	public void givenPostOnTradeValidate_whenSave_thenItSaveTheTrade() throws Exception {
		// ARRANGE

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/trade/validate").with(csrf()).contentType(MediaType.ALL)
				.param("account", "accountPost").param("type", "typePost")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
	}

	@DisplayName("POST not valid : /trade/validate")
	@Test
	public void givenPostNotValidInputOnTradeValidate_whenSave_thenItDoesNotSaveTheTrade() throws Exception {
		// ARRANGE
		Trade trade = new Trade(999, "accountPost", "typePost");

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/trade/validate").with(csrf()).contentType(MediaType.ALL))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<Trade> result = tradeRepository.findById(trade.getTradeId());

		// ASSERT
		assertEquals(status, 200);
		assertFalse(result.isPresent());
	}

	@DisplayName("GET : /trade/update/{id}")
	@Test
	public void givenGetOnTradeUpdateId_whenFindById_thenItFindTheRightTrade() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/trade/update/1")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}

	@DisplayName("POST : /trade/update/{id}")
	@Test
	public void givenPostOnTradeUpdateId_whenSave_thenItSaveTheTrade() throws Exception {
		// ARRANGE
		Trade trade = new Trade(1, "accountUpdate", "typeUpdate");

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/trade/update/1").with(csrf()).contentType(MediaType.ALL)
				.param("account", "accountUpdate").param("type", "typeUpdate")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<Trade> result = tradeRepository.findById(trade.getTradeId());

		// ASSERT
		assertEquals(status, 302);
		assertEquals(result.get().getAccount(), trade.getAccount());
	}

	@DisplayName("POST not valid : /trade/update/{id}")
	@Test
	public void givenPostNotValidInputOnTradeUpdateId_whenSave_thenItDoesNotSaveTheTrade() throws Exception {
		// ARRANGE
		Trade trade = new Trade(1, "accountUpdate", "typeUpdate");

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/trade/update/1").with(csrf()).contentType(MediaType.ALL))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<Trade> result = tradeRepository.findById(trade.getTradeId());

		// ASSERT
		assertEquals(status, 200);
		assertNotEquals(result.get().getAccount(), trade.getAccount());
	}

	@DisplayName("DELETE : /trade/delete/{id}")
	@Test
	public void givenDeleteOnTradeDeleteId_whenDelete_thenItDeleteTheRightTrade() throws Exception {
		// ARRANGE
		Trade trade = new Trade(1, "account", "type");

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/trade/delete/1").with(csrf())).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<Trade> result = tradeRepository.findById(trade.getTradeId());

		// ASSERT
		assertEquals(status, 302);
		assertFalse(result.isPresent());
	}
}
