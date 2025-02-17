package com.nnk.springboot;

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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@DataJpaTest
public class TradeTests {

	@Autowired
	private TradeRepository tradeRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	private Trade trade;

	@DisplayName("Initialize a trade")
	@BeforeEach
	public void initialization() {
		trade = new Trade(1, "Account", "Type");
	}

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	public void injectedComponentsAreRightlySetup() {
		assertThat(tradeRepository).isNotNull();
	}

	@DisplayName("Find by id : trade")
	@Test
	public void givenGettingATrade_whenFindById_thenItReturnTheRightTrade() {
		trade.setTradeId(null); // PersistenceException : can not set an Id
		Trade persistedEntity = testEntityManager.persist(trade);
		Optional<Trade> result = tradeRepository.findById(persistedEntity.getTradeId());
		assertTrue(result.isPresent());
		assertEquals(result.get().getAccount(), trade.getAccount());
	}

	@DisplayName("Find all : trade")
	@Test
	public void givenGettingAllTrade_whenFindAll_thenItReturnAllTheTrades() {
		trade.setTradeId(null); // PersistenceException : can not set an Id
		testEntityManager.persist(trade);
		List<Trade> listResult = tradeRepository.findAll();
		assertTrue(listResult.size() > 0);
	}

	@DisplayName("Save : trade")
	@Test
	public void givenSavingATrade_whenSave_thenItSaveTheTradeInTheDB() {
		trade = tradeRepository.save(trade);
		assertNotNull(tradeRepository.findById(trade.getTradeId()));
		assertEquals(tradeRepository.findById(trade.getTradeId()).get().getAccount(), "Account");
	}

	@DisplayName("Update : trade")
	@Test
	public void givenUpdatingATrade_whenUpdate_thenItUpdateTheTradeInTheDB() {
		trade.setAccount("Account Updated");
		trade = tradeRepository.save(trade);
		assertNotNull(tradeRepository.findById(trade.getTradeId()));
		assertEquals(tradeRepository.findById(trade.getTradeId()).get().getAccount(), "Account Updated");
	}

	@DisplayName("Delete : trade")
	@Test
	public void givenDeletingATrade_whenDelete_thenItDeleteTheTradeInTheDB() {
		trade.setTradeId(null); // PersistenceException : can not set an Id
		Trade persistedEntity = testEntityManager.persist(trade);
		tradeRepository.delete(trade);
		assertFalse(tradeRepository.findById(persistedEntity.getTradeId()).isPresent());
	}

}
