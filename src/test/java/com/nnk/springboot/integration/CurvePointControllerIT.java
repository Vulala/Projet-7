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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CurvePointControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CurvePointRepository curvePointRepository;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	public void injectedComponentsAreRightlySetup() {
		assertThat(mockMvc).isNotNull();
		assertThat(curvePointRepository).isNotNull();

	}

	@DisplayName("GET : /curvePoint/list")
	@Test
	public void givenGetOnCurvePointList_whenFindAll_thenItShowAllTheCurvePoints() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/curvePoint/list")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}

	@DisplayName("GET : /curvePoint/add")
	@Test
	public void givenGetOnCurvePointAdd_thenItReturnTheCurvePointAddForm() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/curvePoint/add")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}

	@DisplayName("POST : /curvePoint/validate")
	@Test
	public void givenPostOnCurvePointValidate_whenSave_thenItSaveTheCurvePoint() throws Exception {
		// ARRANGE

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/curvePoint/validate").with(csrf()).contentType(MediaType.ALL)
				.param("id", "999").param("curveId", "1").param("term", "1.1").param("value", "1.1")).andDo(print())
				.andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
	}

	@DisplayName("POST not valid : /curvePoint/validate")
	@Test
	public void givenPostNotValidInputOnCurvePointValidate_whenSave_thenItDoesNotSaveTheCurvePoint() throws Exception {
		// ARRANGE
		CurvePoint curvePoint = new CurvePoint(999, 1, 1.1, 1.1);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/curvePoint/validate").with(csrf()).contentType(MediaType.ALL))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<CurvePoint> result = curvePointRepository.findById(curvePoint.getId());

		// ASSERT
		assertEquals(status, 200);
		assertFalse(result.isPresent());
	}

	@DisplayName("GET : /curvePoint/update/{id}")
	@Test
	public void givenGetOnCurvePointUpdateId_whenFindById_thenItFindTheRightCurvePoint() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/curvePoint/update/1")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}

	@DisplayName("POST : /curvePoint/update/{id}")
	@Test
	public void givenPostOnCurvePointUpdateId_whenSave_thenItSaveTheCurvePoint() throws Exception {
		// ARRANGE
		CurvePoint curvePoint = new CurvePoint(1, 1, 1.1, 1.1);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/curvePoint/update/1").with(csrf()).contentType(MediaType.ALL)
				.param("id", "1").param("curveId", "1").param("term", "1.1").param("value", "1.1")).andDo(print())
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<CurvePoint> result = curvePointRepository.findById(curvePoint.getId());

		// ASSERT
		assertEquals(status, 302);
		assertEquals(result.get().getTerm(), curvePoint.getTerm());
	}

	@DisplayName("POST not valid : /curvePoint/update/{id}")
	@Test
	public void givenPostNotValidInputOnCurvePointUpdateId_whenSave_thenItDoesNotSaveTheCurvePoint() throws Exception {
		// ARRANGE
		CurvePoint curvePoint = new CurvePoint(1, 1, 1.1, 1.1);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/curvePoint/update/1").with(csrf()).contentType(MediaType.ALL))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<CurvePoint> result = curvePointRepository.findById(curvePoint.getId());

		// ASSERT
		assertEquals(status, 200);
		assertNotEquals(result.get().getTerm(), curvePoint.getTerm());
	}

	@DisplayName("DELETE : /curvePoint/delete/{id}")
	@Test
	public void givenDeleteOnCurvePointDeleteId_whenDelete_thenItDeleteTheRightCurvePoint() throws Exception {
		// ARRANGE
		CurvePoint curvePoint = new CurvePoint(1, 1, 1.1, 1.1);

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/curvePoint/delete/1").with(csrf())).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<CurvePoint> result = curvePointRepository.findById(curvePoint.getId());

		// ASSERT
		assertEquals(status, 302);
		assertFalse(result.isPresent());
	}
}
