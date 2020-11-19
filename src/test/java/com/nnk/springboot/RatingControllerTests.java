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

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.MyUserDetailsService;

@WithMockUser
@WebMvcTest(RatingController.class)
public class RatingControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RatingRepository ratingRepository;

	@MockBean
	private MyUserDetailsService myUserDetailsService;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	public void injectedComponentsAreRightlySetup() {
		assertThat(ratingRepository).isNotNull();
		assertThat(myUserDetailsService).isNotNull();
	}

	@DisplayName("GET : /rating/list")
	@Test
	public void givenGetOnRatingList_whenFindAll_thenItShowAllTheRatings() throws Exception {
		// ARRANGE
		List<Rating> listRating = new ArrayList<Rating>();
		when(ratingRepository.findAll()).thenReturn(listRating);

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/rating/list")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(ratingRepository, times(1)).findAll();
	}

	@DisplayName("GET : /rating/add")
	@Test
	public void givenGetOnRatingAdd_thenItReturnTheRatingAddForm() throws Exception {
		// ARRANGE
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/rating/add")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}

	@DisplayName("POST : /rating/validate")
	@Test
	public void givenPostOnRatingValidate_whenSave_thenItSaveTheRating() throws Exception {
		// ARRANGE
		Rating rating = new Rating(1, "moodysRating", "sandRating", "fitchRating", 1);
		when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

		// ACT
		MvcResult mvcResult = mockMvc.perform(
				post("/rating/validate").with(csrf()).contentType(MediaType.ALL).param("moodysRating", "moodysRating")
						.param("sandRating", "sandRating").param("fitchRating", "fitchRating"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
		verify(ratingRepository, times(1)).save(any(Rating.class));
	}

	@DisplayName("POST not valid : /rating/validate")
	@Test
	public void givenPostNotValidInputOnRatingValidate_whenSave_thenItDoesNotSaveTheRating() throws Exception {
		// ARRANGE
		Rating rating = new Rating(1, "moodysRating", "sandRating", "fitchRating", 1);
		when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/rating/validate").with(csrf()).contentType(MediaType.ALL))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(ratingRepository, times(0)).save(any(Rating.class));
	}

	@DisplayName("GET : /rating/update/{id}")
	@Test
	public void givenGetOnRatingUpdateId_whenFindById_thenItFindTheRightRating() throws Exception {
		// ARRANGE
		Rating rating = new Rating(1, "moodysRating", "sandRating", "fitchRating", 1);
		Optional<Rating> ratingOptional = Optional.of(rating);
		when(ratingRepository.findById(any(Integer.class))).thenReturn(ratingOptional);

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/rating/update/1")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(ratingRepository, times(1)).findById(1);
	}

	@DisplayName("POST : /rating/update/{id}")
	@Test
	public void givenPostOnRatingUpdateId_whenSave_thenItSaveTheRating() throws Exception {
		// ARRANGE
		Rating rating = new Rating(1, "moodysRating", "sandRating", "fitchRating", 1);
		when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

		// ACT
		MvcResult mvcResult = mockMvc.perform(
				post("/rating/update/1").with(csrf()).contentType(MediaType.ALL).param("moodysRating", "moodysRating")
						.param("sandRating", "sandRating").param("fitchRating", "fitchRating"))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
		verify(ratingRepository, times(1)).save(any(Rating.class));
	}

	@DisplayName("POST not valid : /rating/update/{id}")
	@Test
	public void givenPostNotValidInputOnRatingUpdateId_whenSave_thenItDoesNotSaveTheRating() throws Exception {
		// ARRANGE
		Rating rating = new Rating(1, "moodysRating", "sandRating", "fitchRating", 1);
		when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/rating/update/1").with(csrf()).contentType(MediaType.ALL))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
		verify(ratingRepository, times(0)).save(any(Rating.class));
	}

	@DisplayName("DELETE : /rating/delete/{id}")
	@Test
	public void givenDeleteOnRatingDeleteId_whenDelete_thenItDeleteTheRightRating() throws Exception {
		// ARRANGE
		Rating rating = new Rating(1, "moodysRating", "sandRating", "fitchRating", 1);
		Optional<Rating> ratingOptional = Optional.of(rating);
		when(ratingRepository.findById(any(Integer.class))).thenReturn(ratingOptional);
		Mockito.doNothing().when(ratingRepository).delete(any(Rating.class));

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/rating/delete/1").with(csrf())).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
		verify(ratingRepository, times(1)).delete(any(Rating.class));
	}
}
