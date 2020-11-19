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

import com.nnk.springboot.controllers.CurvePointController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.MyUserDetailsService;

@WithMockUser
@WebMvcTest(CurvePointController.class)
public class CurvePointControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CurvePointRepository curvePointRepository;

	@MockBean
	private MyUserDetailsService myUserDetailsService;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	public void injectedComponentsAreRightlySetup() {
		assertThat(curvePointRepository).isNotNull();
		assertThat(myUserDetailsService).isNotNull();
	}

	@DisplayName("GET : /curvePoint/list")
	@Test
	public void givenGetOnCurvePointList_whenFindAll_thenItShowAllTheCurvePoints() throws Exception {
		// ARRANGE
		List<CurvePoint> listCurvePoint = new ArrayList<CurvePoint>();
		when(curvePointRepository.findAll()).thenReturn(listCurvePoint);

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/curvePoint/list")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(curvePointRepository, times(1)).findAll();
	}

	@DisplayName("GET : /curvePoint/add")
	@Test
	public void givenGetOnCurvePointAdd_thenItReturnTheCurvePointAddForm() throws Exception {
		// ARRANGE
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
		CurvePoint curvePoint = new CurvePoint(1, 0, 0.0, 1.0);
		when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/curvePoint/validate").with(csrf()).contentType(MediaType.ALL)
				.param("curveId", "1")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
		verify(curvePointRepository, times(1)).save(any(CurvePoint.class));
	}

	@DisplayName("POST not valid : /curvePoint/validate")
	@Test
	public void givenPostNotValidInputOnCurvePointValidate_whenSave_thenItDoesNotSaveTheCurvePoint() throws Exception {
		// ARRANGE
		CurvePoint curvePoint = new CurvePoint(1, 0, 0.0, 1.0);
		when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/curvePoint/validate").with(csrf()).contentType(MediaType.ALL))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(curvePointRepository, times(0)).save(any(CurvePoint.class));
	}

	@DisplayName("GET : /curvePoint/update/{id}")
	@Test
	public void givenGetOnCurvePointUpdateId_whenFindById_thenItFindTheRightCurvePoint() throws Exception {
		// ARRANGE
		CurvePoint curvePoint = new CurvePoint(1, 0, 0.0, 1.0);
		Optional<CurvePoint> curvePointOptional = Optional.of(curvePoint);
		when(curvePointRepository.findById(any(Integer.class))).thenReturn(curvePointOptional);

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/curvePoint/update/1")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(curvePointRepository, times(1)).findById(1);
	}

	@DisplayName("POST : /curvePoint/update/{id}")
	@Test
	public void givenPostOnCurvePointUpdateId_whenSave_thenItSaveTheCurvePoint() throws Exception {
		// ARRANGE
		CurvePoint curvePoint = new CurvePoint(1, 0, 0.0, 1.0);
		when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/curvePoint/update/1").with(csrf()).contentType(MediaType.ALL)
				.param("curveId", "1")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
		verify(curvePointRepository, times(1)).save(any(CurvePoint.class));
	}

	@DisplayName("POST not valid : /curvePoint/update/{id}")
	@Test
	public void givenPostNotValidInputOnCurvePointUpdateId_whenSave_thenItDoesNotSaveTheCurvePoint() throws Exception {
		// ARRANGE
		CurvePoint curvePoint = new CurvePoint(1, 0, 0.0, 1.0);
		when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/curvePoint/update/1").with(csrf()).contentType(MediaType.ALL))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(curvePointRepository, times(0)).save(any(CurvePoint.class));
	}

	@DisplayName("DELETE : /curvePoint/delete/{id}")
	@Test
	public void givenDeleteOnCurvePointDeleteId_whenDelete_thenItDeleteTheRightCurvePoint() throws Exception {
		// ARRANGE
		CurvePoint curvePoint = new CurvePoint(1, 0, 0.0, 1.0);
		Optional<CurvePoint> curvePointOptional = Optional.of(curvePoint);
		when(curvePointRepository.findById(any(Integer.class))).thenReturn(curvePointOptional);
		Mockito.doNothing().when(curvePointRepository).delete(any(CurvePoint.class));

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/curvePoint/delete/1").with(csrf())).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
		verify(curvePointRepository, times(1)).delete(any(CurvePoint.class));
	}
}
