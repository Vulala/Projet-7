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

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RuleNameControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	public void injectedComponentsAreRightlySetup() {
		assertThat(mockMvc).isNotNull();
		assertThat(ruleNameRepository).isNotNull();

	}

	@DisplayName("GET : /ruleName/list")
	@Test
	public void givenGetOnRuleNameList_whenFindAll_thenItShowAllTheRuleNames() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/ruleName/list")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}

	@DisplayName("GET : /ruleName/add")
	@Test
	public void givenGetOnRuleNameAdd_thenItReturnTheRuleNameAddForm() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/ruleName/add")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}

	@DisplayName("POST : /ruleName/validate")
	@Test
	public void givenPostOnRuleNameValidate_whenSave_thenItSaveTheRuleName() throws Exception {
		// ARRANGE

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/ruleName/validate").with(csrf()).contentType(MediaType.ALL)
				.param("id", "999").param("name", "namePost").param("description", "descriptionPost")
				.param("json", "jsonPost").param("template", "templatePost").param("sqlStr", "sqlStrPost")
				.param("sqlPart", "sqlPartPost")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
	}

	@DisplayName("POST not valid : /ruleName/validate")
	@Test
	public void givenPostNotValidInputOnRuleNameValidate_whenSave_thenItDoesNotSaveTheRuleName() throws Exception {
		// ARRANGE
		RuleName ruleName = new RuleName(999, "namePost", "descriptionPost", "jsonPost", "templatePost", "sqlStrPost",
				"sqlPartPost");

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/ruleName/validate").with(csrf()).contentType(MediaType.ALL))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<RuleName> result = ruleNameRepository.findById(ruleName.getId());

		// ASSERT
		assertEquals(status, 200);
		assertFalse(result.isPresent());
	}

	@DisplayName("GET : /ruleName/update/{id}")
	@Test
	public void givenGetOnRuleNameUpdateId_whenFindById_thenItFindTheRightRuleName() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/ruleName/update/1")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}

	@DisplayName("POST : /ruleName/update/{id}")
	@Test
	public void givenPostOnRuleNameUpdateId_whenSave_thenItSaveTheRuleName() throws Exception {
		// ARRANGE
		RuleName ruleName = new RuleName(1, "nameUpdate", "descriptionUpdate", "jsonUpdate", "templateUpdate",
				"sqlStrUpdate", "sqlPartUpdate");

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/ruleName/update/1").with(csrf()).contentType(MediaType.ALL)
				.param("id", "1").param("name", "nameUpdate").param("description", "descriptionUpdate")
				.param("json", "jsonUpdate").param("template", "templateUpdate").param("sqlStr", "sqlStrUpdate")
				.param("sqlPart", "sqlPartUpdate")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<RuleName> result = ruleNameRepository.findById(ruleName.getId());

		// ASSERT
		assertEquals(status, 302);
		assertEquals(result.get().getName(), ruleName.getName());
	}

	@DisplayName("POST not valid : /ruleName/update/{id}")
	@Test
	public void givenPostNotValidInputOnRuleNameUpdateId_whenSave_thenItDoesNotSaveTheRuleName() throws Exception {
		// ARRANGE
		RuleName ruleName = new RuleName(1, "nameUpdate", "descriptionUpdate", "jsonUpdate", "templateUpdate",
				"sqlStrUpdate", "sqlPartUpdate");

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/ruleName/update/1").with(csrf()).contentType(MediaType.ALL))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<RuleName> result = ruleNameRepository.findById(ruleName.getId());

		// ASSERT
		assertEquals(status, 200);
		assertNotEquals(result.get().getName(), ruleName.getName());
	}

	@DisplayName("DELETE : /ruleName/delete/{id}")
	@Test
	public void givenDeleteOnRuleNameDeleteId_whenDelete_thenItDeleteTheRightRuleName() throws Exception {
		// ARRANGE
		RuleName ruleName = new RuleName(1, "name", "description", "json", "template", "sqlStr", "sqlPart");

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/ruleName/delete/1").with(csrf())).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<RuleName> result = ruleNameRepository.findById(ruleName.getId());

		// ASSERT
		assertEquals(status, 302);
		assertFalse(result.isPresent());
	}
}
