package com.nnk.springboot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.MyUserDetailsService;

@WithMockUser
@WebMvcTest(TradeController.class)
public class TradeControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TradeRepository tradeRepository;

	@MockBean
	private MyUserDetailsService myUserDetailsService;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	public void injectedComponentsAreRightlySetup() {
		assertThat(tradeRepository).isNotNull();
		assertThat(myUserDetailsService).isNotNull();
	}

	@DisplayName("GET : /trade/list")
	@Test
	public void givenGetOnTradeList_whenFindAll_thenItShowAllTheTrades() throws Exception {
		// ARRANGE
		List<Trade> listTrade = new ArrayList<Trade>();
		when(tradeRepository.findAll()).thenReturn(listTrade);

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/trade/list")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(tradeRepository, times(1)).findAll();
	}

	@DisplayName("GET : /trade/add")
	@Test
	public void givenGetOnTradeAdd_thenItReturnTheTradeAddForm() throws Exception {
		// ARRANGE
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
		Trade trade = new Trade(1, "account", "type");
		when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/trade/validate").with(csrf()).contentType(MediaType.ALL)
				.param("account", "account").param("type", "type")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
		verify(tradeRepository, times(1)).save(any(Trade.class));
	}

	@DisplayName("POST not valid : /trade/validate")
	@Test
	public void givenPostNotValidInputOnTradeValidate_whenSave_thenItDoesNotSaveTheTrade() throws Exception {
		// ARRANGE
		Trade trade = new Trade(1, "account", "type");
		when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/trade/validate").with(csrf()).contentType(MediaType.ALL))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(tradeRepository, times(0)).save(any(Trade.class));
	}

	@DisplayName("GET : /trade/update/{id}")
	@Test
	public void givenGetOnTradeUpdateId_whenFindById_thenItFindTheRightTrade() throws Exception {
		// ARRANGE
		Trade trade = new Trade(1, "account", "type");
		Optional<Trade> tradeOptional = Optional.of(trade);
		when(tradeRepository.findById(any(Integer.class))).thenReturn(tradeOptional);

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/trade/update/1")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(tradeRepository, times(1)).findById(1);
	}

	@DisplayName("POST : /trade/update/{id}")
	@Test
	public void givenPostOnTradeUpdateId_whenSave_thenItSaveTheTrade() throws Exception {
		// ARRANGE
		Trade trade = new Trade(1, "account", "type");
		when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/trade/update/1").with(csrf()).contentType(MediaType.ALL)
				.param("account", "account").param("type", "type")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
		verify(tradeRepository, times(1)).save(any(Trade.class));
	}

	@DisplayName("POST not valid : /trade/update/{id}")
	@Test
	public void givenPostNotValidInputOnTradeUpdateId_whenSave_thenItDoesNotSaveTheTrade() throws Exception {
		// ARRANGE
		Trade trade = new Trade(1, "account", "type");
		when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/trade/update/1").with(csrf()).contentType(MediaType.ALL))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(tradeRepository, times(0)).save(any(Trade.class));
	}

	@DisplayName("DELETE : /trade/delete/{id}")
	@Test
	public void givenDeleteOnTradeDeleteId_whenDelete_thenItDeleteTheRightTrade() throws Exception {
		// ARRANGE
		Trade trade = new Trade(1, "account", "type");
		Optional<Trade> tradeOptional = Optional.of(trade);
		when(tradeRepository.findById(any(Integer.class))).thenReturn(tradeOptional);
		Mockito.doNothing().when(tradeRepository).delete(any(Trade.class));

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/trade/delete/1").with(csrf())).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
		verify(tradeRepository, times(1)).delete(any(Trade.class));
	}
}
