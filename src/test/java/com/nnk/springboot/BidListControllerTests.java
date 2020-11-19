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

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.MyUserDetailsService;

@WithMockUser
@WebMvcTest(BidListController.class)
public class BidListControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BidListRepository bidListRepository;

	@MockBean
	private MyUserDetailsService myUserDetailsService;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	public void injectedComponentsAreRightlySetup() {
		assertThat(bidListRepository).isNotNull();
		assertThat(myUserDetailsService).isNotNull();
	}

	@DisplayName("GET : /bidList/list")
	@Test
	public void givenGetOnBidListList_whenFindAll_thenItShowAllTheBidLists() throws Exception {
		// ARRANGE
		List<BidList> listBidList = new ArrayList<BidList>();
		when(bidListRepository.findAll()).thenReturn(listBidList);

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/bidList/list")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(bidListRepository, times(1)).findAll();
	}

	@DisplayName("GET : /bidList/add")
	@Test
	public void givenGetOnBidListAdd_thenItReturnTheBidListAddForm() throws Exception {
		// ARRANGE
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/bidList/add")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}

	@DisplayName("POST : /bidList/validate")
	@Test
	public void givenPostOnBidListValidate_whenSave_thenItSaveTheBidList() throws Exception {
		// ARRANGE
		BidList bidList = new BidList(1, "account", "type", 1.0);
		when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/bidList/validate").with(csrf()).contentType(MediaType.ALL)
				.param("account", "account").param("type", "type")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
		verify(bidListRepository, times(1)).save(any(BidList.class));
	}

	@DisplayName("POST not valid : /bidList/validate")
	@Test
	public void givenPostNotValidInputOnBidListValidate_whenSave_thenItDoesNotSaveTheBidList() throws Exception {
		// ARRANGE
		BidList bidList = new BidList(1, "account", "type", 1.0);
		when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/bidList/validate").with(csrf()).contentType(MediaType.ALL))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(bidListRepository, times(0)).save(any(BidList.class));
	}

	@DisplayName("GET : /bidList/update/{id}")
	@Test
	public void givenGetOnBidListUpdateId_whenFindById_thenItFindTheRightBidList() throws Exception {
		// ARRANGE
		BidList bidList = new BidList(1, "account", "type", 1.0);
		Optional<BidList> bidListOptional = Optional.of(bidList);
		when(bidListRepository.findById(any(Integer.class))).thenReturn(bidListOptional);

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/bidList/update/1")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(bidListRepository, times(1)).findById(1);
	}

	@DisplayName("POST : /bidList/update/{id}")
	@Test
	public void givenPostOnBidListUpdateId_whenSave_thenItSaveTheBidList() throws Exception {
		// ARRANGE
		BidList bidList = new BidList(1, "account", "type", 1.0);
		when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/bidList/update/1").with(csrf()).contentType(MediaType.ALL)
				.param("account", "account").param("type", "type")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
		verify(bidListRepository, times(1)).save(any(BidList.class));
	}

	@DisplayName("POST not valid : /bidList/update/{id}")
	@Test
	public void givenPostNotValidInputOnBidListUpdateId_whenSave_thenItDoesNotSaveTheBidList() throws Exception {
		// ARRANGE
		BidList bidList = new BidList(1, "account", "type", 1.0);
		when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/bidList/update/1").with(csrf()).contentType(MediaType.ALL))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(bidListRepository, times(0)).save(any(BidList.class));
	}

	@DisplayName("DELETE : /bidList/delete/{id}")
	@Test
	public void givenDeleteOnBidListDeleteId_whenDelete_thenItDeleteTheRightBidList() throws Exception {
		// ARRANGE
		BidList bidList = new BidList(1, "account", "type", 1.0);
		Optional<BidList> bidListOptional = Optional.of(bidList);
		when(bidListRepository.findById(any(Integer.class))).thenReturn(bidListOptional);
		Mockito.doNothing().when(bidListRepository).delete(any(BidList.class));

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/bidList/delete/1").with(csrf())).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
		verify(bidListRepository, times(1)).delete(any(BidList.class));
	}
}
