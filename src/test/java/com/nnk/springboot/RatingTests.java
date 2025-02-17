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

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@DataJpaTest
public class RatingTests {

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	private Rating rating;

	@DisplayName("Initialize a rating")
	@BeforeEach
	public void initialization() {
		rating = new Rating(1, "Moodys Rating", "Sand PRating", "Fitch Rating", 10);
	}

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	public void injectedComponentsAreRightlySetup() {
		assertThat(ratingRepository).isNotNull();
	}

	@DisplayName("Find by id : rating")
	@Test
	public void givenGettingARating_whenFindById_thenItReturnTheRightRating() {
		rating.setId(null); // PersistenceException : can not set an Id
		Rating persistedEntity = testEntityManager.persist(rating);
		Optional<Rating> result = ratingRepository.findById(persistedEntity.getId());
		assertTrue(result.isPresent());
		assertEquals(result.get().getMoodysRating(), rating.getMoodysRating());
	}

	@DisplayName("Find all : rating")
	@Test
	public void givenGettingAllRating_whenFindAll_thenItReturnAllTheRatings() {
		rating.setId(null); // PersistenceException : can not set an Id
		testEntityManager.persist(rating);
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
		rating.setId(null); // PersistenceException : can not set an Id
		Rating persistedEntity = testEntityManager.persist(rating);
		ratingRepository.delete(rating);
		assertFalse(ratingRepository.findById(persistedEntity.getId()).isPresent());
	}

}
