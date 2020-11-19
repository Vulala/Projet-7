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

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RatingControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RatingRepository ratingRepository;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	public void injectedComponentsAreRightlySetup() {
		assertThat(mockMvc).isNotNull();
		assertThat(ratingRepository).isNotNull();

	}

	@DisplayName("GET : /rating/list")
	@Test
	public void givenGetOnRatingList_whenFindAll_thenItShowAllTheRatings() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/rating/list")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}

	@DisplayName("GET : /rating/add")
	@Test
	public void givenGetOnRatingAdd_thenItReturnTheRatingAddForm() throws Exception {
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

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/rating/validate").with(csrf()).contentType(MediaType.ALL)
				.param("id", "999").param("moodysRating", "moodysRatingPost").param("sandRating", "sandRatingPost")
				.param("fitchRating", "fitchRatingPost")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 302);
	}

	@DisplayName("POST not valid : /rating/validate")
	@Test
	public void givenPostNotValidInputOnRatingValidate_whenSave_thenItDoesNotSaveTheRating() throws Exception {
		// ARRANGE
		Rating rating = new Rating(999, "moodysRatingPost", "sandRatingPost", "fitchRatingPost", 1);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/rating/validate").with(csrf()).contentType(MediaType.ALL))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<Rating> result = ratingRepository.findById(rating.getId());

		// ASSERT
		assertEquals(status, 200);
		assertFalse(result.isPresent());
	}

	@DisplayName("GET : /rating/update/{id}")
	@Test
	public void givenGetOnRatingUpdateId_whenFindById_thenItFindTheRightRating() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/rating/update/1")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(status, 200);
	}

	@DisplayName("POST : /rating/update/{id}")
	@Test
	public void givenPostOnRatingUpdateId_whenSave_thenItSaveTheRating() throws Exception {
		// ARRANGE
		Rating rating = new Rating(1, "moodysRatingUpdate", "sandRatingUpdate", "fitchRatingUpdate", 1);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/rating/update/1").with(csrf()).contentType(MediaType.ALL)
				.param("id", "1").param("moodysRating", "moodysRatingUpdate").param("sandRating", "sandRatingUpdate")
				.param("fitchRating", "fitchRatingUpdate")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<Rating> result = ratingRepository.findById(rating.getId());

		// ASSERT
		assertEquals(status, 302);
		assertEquals(result.get().getMoodysRating(), rating.getMoodysRating());
	}

	@DisplayName("POST not valid : /rating/update/{id}")
	@Test
	public void givenPostNotValidInputOnRatingUpdateId_whenSave_thenItDoesNotSaveTheRating() throws Exception {
		// ARRANGE
		Rating rating = new Rating(1, "moodysRatingUpdate", "sandRatingUpdate", "fitchRatingUpdate", 1);

		// ACT
		MvcResult mvcResult = mockMvc.perform(post("/rating/update/1").with(csrf()).contentType(MediaType.ALL))
				.andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<Rating> result = ratingRepository.findById(rating.getId());

		// ASSERT
		assertEquals(status, 200);
		assertNotEquals(result.get().getMoodysRating(), rating.getMoodysRating());
	}

	@DisplayName("DELETE : /rating/delete/{id}")
	@Test
	public void givenDeleteOnRatingDeleteId_whenDelete_thenItDeleteTheRightRating() throws Exception {
		// ARRANGE
		Rating rating = new Rating(1, "moodysRating", "sandRating", "fitchRating", 1);

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/rating/delete/1").with(csrf())).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Optional<Rating> result = ratingRepository.findById(rating.getId());

		// ASSERT
		assertEquals(status, 302);
		assertFalse(result.isPresent());
	}
}
