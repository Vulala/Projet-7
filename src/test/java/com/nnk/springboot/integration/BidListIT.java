package com.nnk.springboot.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BidListIT {

	@Autowired
	private BidListRepository bidListRepository;

	private BidList bidList;

	@DisplayName("Initialize a bidList")
	@BeforeEach
	public void initialization() {
		bidList = new BidList(1, "Account Test", "Type Test", 10d);
	}

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	public void injectedComponentsAreRightlySetup() {
		assertThat(bidListRepository).isNotNull();
	}

	@DisplayName("Find by id : bidList")
	@Test
	public void givenGettingABidList_whenFindById_thenItReturnTheRightBidList() {
		Optional<BidList> result = bidListRepository.findById(bidList.getBidListId());
		assertTrue(result.isPresent());
	}

	@DisplayName("Find all : bidList")
	@Test
	public void givenGettingAllBidList_whenFindAll_thenItReturnAllTheBidLists() {
		List<BidList> listResult = bidListRepository.findAll();
		assertTrue(listResult.size() > 0);
	}

	@DisplayName("Save : bidList")
	@Test
	public void givenSavingABidList_whenSave_thenItSaveTheBidListInTheDB() {
		bidList = bidListRepository.save(bidList);
		assertNotNull(bidListRepository.findById(bidList.getBidListId()));
		assertEquals(bidListRepository.findById(bidList.getBidListId()).get().getBidQuantity(), 10d);
	}

	@DisplayName("Update : bidList")
	@Test
	public void givenUpdatingABidList_whenUpdate_thenItUpdateTheBidListInTheDB() {
		bidList.setBidQuantity(20d);
		bidList = bidListRepository.save(bidList);
		assertNotNull(bidListRepository.findById(bidList.getBidListId()));
		assertEquals(bidListRepository.findById(bidList.getBidListId()).get().getBidQuantity(), 20d);
	}

	@DisplayName("Delete : bidList")
	@Test
	public void givenDeletingABidList_whenDelete_thenItDeleteTheBidListInTheDB() {
		bidListRepository.delete(bidList);
		assertFalse(bidListRepository.findById(bidList.getBidListId()).isPresent());
	}

}
