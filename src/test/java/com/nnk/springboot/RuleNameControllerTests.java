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

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.MyUserDetailsService;

@WithMockUser
@WebMvcTest(RuleNameController.class)
public class RuleNameControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RuleNameRepository ruleNameRepository;

	@MockBean
	private MyUserDetailsService myUserDetailsService;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	public void injectedComponentsAreRightlySetup() {
		assertThat(ruleNameRepository).isNotNull();
		assertThat(myUserDetailsService).isNotNull();
	}

	@DisplayName("GET : /ruleName/list")
	@Test
	public void givenGetOnRuleNameList_whenFindAll_thenItShowAllTheRuleNames() throws Exception {
		// ARRANGE
		List<RuleName> listRuleName = new ArrayList<RuleName>();
		when(ruleNameRepository.findAll()).thenReturn(listRuleName);

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/ruleName/list")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(ruleNameRepository, times(1)).findAll();
	}

	@DisplayName("GET : /ruleName/add")
	@Test
	public void givenGetOnRuleNameAdd_thenItReturnTheRuleNameAddForm() throws Exception {
		// ARRANGE
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
		RuleName ruleName = new RuleName(1, "name", "description", "json", "template", "sqlStr", "sqlPart");
		when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/ruleName/validate").with(csrf()).contentType(MediaType.ALL)
				.param("name", "name").param("description", "description").param("json", "json")
				.param("template", "template").param("sqlStr", "sqlStr").param("sqlPart", "sqlPart")).andDo(print())
				.andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
		verify(ruleNameRepository, times(1)).save(any(RuleName.class));
	}

	@DisplayName("POST not valid : /ruleName/validate")
	@Test
	public void givenPostNotValidInputOnRuleNameValidate_whenSave_thenItDoesNotSaveTheRuleName() throws Exception {
		// ARRANGE
		RuleName ruleName = new RuleName(1, "name", "description", "json", "template", "sqlStr", "sqlPart");
		when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/ruleName/validate").with(csrf()).contentType(MediaType.ALL))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(ruleNameRepository, times(0)).save(any(RuleName.class));
	}

	@DisplayName("GET : /ruleName/update/{id}")
	@Test
	public void givenGetOnRuleNameUpdateId_whenFindById_thenItFindTheRightRuleName() throws Exception {
		// ARRANGE
		RuleName ruleName = new RuleName(1, "name", "description", "json", "template", "sqlStr", "sqlPart");
		Optional<RuleName> ruleNameOptional = Optional.of(ruleName);
		when(ruleNameRepository.findById(any(Integer.class))).thenReturn(ruleNameOptional);

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/ruleName/update/1")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(ruleNameRepository, times(1)).findById(1);
	}

	@DisplayName("POST : /ruleName/update/{id}")
	@Test
	public void givenPostOnRuleNameUpdateId_whenSave_thenItSaveTheRuleName() throws Exception {
		// ARRANGE
		RuleName ruleName = new RuleName(1, "name", "description", "json", "template", "sqlStr", "sqlPart");
		when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/ruleName/update/1").with(csrf()).contentType(MediaType.ALL)
				.param("name", "name").param("description", "description").param("json", "json")
				.param("template", "template").param("sqlStr", "sqlStr").param("sqlPart", "sqlPart")).andDo(print())
				.andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
		verify(ruleNameRepository, times(1)).save(any(RuleName.class));
	}

	@DisplayName("POST not valid : /ruleName/update/{id}")
	@Test
	public void givenPostNotValidInputOnRuleNameUpdateId_whenSave_thenItDoesNotSaveTheRuleName() throws Exception {
		// ARRANGE
		RuleName ruleName = new RuleName(1, "name", "description", "json", "template", "sqlStr", "sqlPart");
		when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/ruleName/update/1").with(csrf()).contentType(MediaType.ALL))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(ruleNameRepository, times(0)).save(any(RuleName.class));
	}

	@DisplayName("DELETE : /ruleName/delete/{id}")
	@Test
	public void givenDeleteOnRuleNameDeleteId_whenDelete_thenItDeleteTheRightRuleName() throws Exception {
		// ARRANGE
		RuleName ruleName = new RuleName(1, "name", "description", "json", "template", "sqlStr", "sqlPart");
		Optional<RuleName> ruleNameOptional = Optional.of(ruleName);
		when(ruleNameRepository.findById(any(Integer.class))).thenReturn(ruleNameOptional);
		Mockito.doNothing().when(ruleNameRepository).delete(any(RuleName.class));

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/ruleName/delete/1").with(csrf())).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
		verify(ruleNameRepository, times(1)).delete(any(RuleName.class));
	}
}
