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

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

@DataJpaTest
public class RuleNameTests {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	private RuleName ruleName;

	@DisplayName("Initialize a ruleName")
	@BeforeEach
	public void initialization() {
		ruleName = new RuleName(1, "Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
	}

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	public void injectedComponentsAreRightlySetup() {
		assertThat(ruleNameRepository).isNotNull();
	}

	@DisplayName("Find by id : ruleName")
	@Test
	public void givenGettingARuleName_whenFindById_thenItReturnTheRightRuleName() {
		ruleName.setId(null); // PersistenceException : can not set an Id
		RuleName persistedEntity = testEntityManager.persist(ruleName);
		Optional<RuleName> result = ruleNameRepository.findById(persistedEntity.getId());
		assertTrue(result.isPresent());
		assertEquals(result.get().getName(), ruleName.getName());
	}

	@DisplayName("Find all : ruleName")
	@Test
	public void givenGettingAllRuleName_whenFindAll_thenItReturnAllTheRuleNames() {
		ruleName.setId(null); // PersistenceException : can not set an Id
		testEntityManager.persist(ruleName);
		List<RuleName> listResult = ruleNameRepository.findAll();
		assertTrue(listResult.size() > 0);
	}

	@DisplayName("Save : ruleName")
	@Test
	public void givenSavingARuleName_whenSave_thenItSaveTheRuleNameInTheDB() {
		ruleName = ruleNameRepository.save(ruleName);
		assertNotNull(ruleNameRepository.findById(ruleName.getId()));
		assertEquals(ruleNameRepository.findById(ruleName.getId()).get().getName(), "Rule Name");
	}

	@DisplayName("Update : ruleName")
	@Test
	public void givenUpdatingARuleName_whenUpdate_thenItUpdateTheRuleNameInTheDB() {
		ruleName.setName("Rule Name Updated");
		ruleName = ruleNameRepository.save(ruleName);
		assertNotNull(ruleNameRepository.findById(ruleName.getId()));
		assertEquals(ruleNameRepository.findById(ruleName.getId()).get().getName(), "Rule Name Updated");
	}

	@DisplayName("Delete : ruleName")
	@Test
	public void givenDeletingARuleName_whenDelete_thenItDeleteTheRuleNameInTheDB() {
		ruleName.setId(null); // PersistenceException : can not set an Id
		RuleName persistedEntity = testEntityManager.persist(ruleName);
		ruleNameRepository.delete(ruleName);
		assertFalse(ruleNameRepository.findById(persistedEntity.getId()).isPresent());
	}

}
