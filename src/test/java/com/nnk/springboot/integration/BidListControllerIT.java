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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BidListControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BidListRepository bidListRepository;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	public void injectedComponentsAreRightlySetup() {
		assertThat(mockMvc).isNotNull();
		assertThat(bidListRepository).isNotNull();
	}

	@DisplayName("GET : /bidList/list")
	@Test
	public void givenGetOnBidListList_whenFindAll_thenItShowAllTheBidLists() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/bidList/list")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}

	@DisplayName("GET : /bidList/add")
	@Test
	public void givenGetOnBidListAdd_thenItReturnTheBidListAddForm() throws Exception {
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

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/bidList/validate").with(csrf()).contentType(MediaType.ALL)
				.param("account", "accountPost").param("type", "typePost")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
	}

	@DisplayName("POST not valid : /bidList/validate")
	@Test
	public void givenPostNotValidInputOnBidListValidate_whenSave_thenItDoesNotSaveTheBidList() throws Exception {
		// ARRANGE
		BidList bidList = new BidList(999, "accountPost", "typePost", 1.0);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/bidList/validate").with(csrf()).contentType(MediaType.ALL))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<BidList> result = bidListRepository.findById(bidList.getBidListId());

		// ASSERT
		assertEquals(status, 200);
		assertFalse(result.isPresent());
	}

	@DisplayName("GET : /bidList/update/{id}")
	@Test
	public void givenGetOnBidListUpdateId_whenFindById_thenItFindTheRightBidList() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/bidList/update/1")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}

	@DisplayName("POST : /bidList/update/{id}")
	@Test
	public void givenPostOnBidListUpdateId_whenSave_thenItSaveTheBidList() throws Exception {
		// ARRANGE
		BidList bidList = new BidList(1, "accountUpdate", "typeUpdate", 1.0);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/bidList/update/1").with(csrf()).contentType(MediaType.ALL)
				.param("account", "accountUpdate").param("type", "typeUpdate")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<BidList> result = bidListRepository.findById(bidList.getBidListId());

		// ASSERT
		assertEquals(status, 302);
		assertEquals(result.get().getAccount(), bidList.getAccount());
	}

	@DisplayName("POST not valid : /bidList/update/{id}")
	@Test
	public void givenPostNotValidInputOnBidListUpdateId_whenSave_thenItDoesNotSaveTheBidList() throws Exception {
		// ARRANGE
		BidList bidList = new BidList(1, "accountUpdate", "typeUpdate", 1.0);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/bidList/update/1").with(csrf()).contentType(MediaType.ALL))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<BidList> result = bidListRepository.findById(bidList.getBidListId());

		// ASSERT
		assertEquals(status, 200);
		assertNotEquals(result.get().getAccount(), bidList.getAccount());
	}

	@DisplayName("DELETE : /bidList/delete/{id}")
	@Test
	public void givenDeleteOnBidListDeleteId_whenDelete_thenItDeleteTheRightBidList() throws Exception {
		// ARRANGE
		BidList bidList = new BidList(1, "account", "type", 1.0);

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/bidList/delete/1").with(csrf())).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<BidList> result = bidListRepository.findById(bidList.getBidListId());

		// ASSERT
		assertEquals(status, 302);
		assertFalse(result.isPresent());
	}
}
