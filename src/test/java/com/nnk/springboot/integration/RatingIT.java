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

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RatingIT {

	@Autowired
	private RatingRepository ratingRepository;

	private Rating rating;

	@DisplayName("Initialize a rating")
	@BeforeEach
	public void initialization() {
		rating = new Rating(1, "Moodys Rating", "Sand Rating", "Fitch Rating", 10);
	}

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	public void injectedComponentsAreRightlySetup() {
		assertThat(ratingRepository).isNotNull();
	}

	@DisplayName("Find by id : rating")
	@Test
	public void givenGettingARating_whenFindById_thenItReturnTheRightRating() {
		Optional<Rating> result = ratingRepository.findById(rating.getId());
		assertTrue(result.isPresent());
	}

	@DisplayName("Find all : rating")
	@Test
	public void givenGettingAllRating_whenFindAll_thenItReturnAllTheRatings() {
		List<Rating> listResult = ratingRepository.findAll();
		assertTrue(listResult.size() > 0);
	}

	@DisplayName("Save : rating")
	@Test
	public void givenSavingARating_whenSave_thenItSaveTheRatingInTheDB() {
		rating = ratingRepository.save(rating);
		assertNotNull(ratingRepository.findById(rating.getId()));
		assertEquals(ratingRepository.findById(rating.getId()).get().getOrderNumber(), 10);
	}

	@DisplayName("Update : rating")
	@Test
	public void givenUpdatingARating_whenUpdate_thenItUpdateTheRatingInTheDB() {
		rating.setOrderNumber(20);
		rating = ratingRepository.save(rating);
		assertNotNull(ratingRepository.findById(rating.getId()));
		assertEquals(ratingRepository.findById(rating.getId()).get().getOrderNumber(), 20);
	}

	@DisplayName("Delete : rating")
	@Test
	public void givenDeletingARating_whenDelete_thenItDeleteTheRatingInTheDB() {
		ratingRepository.delete(rating);
		assertFalse(ratingRepository.findById(rating.getId()).isPresent());
	}

}
