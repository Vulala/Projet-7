package com.nnk.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.domain.Rating;

/**
 * Interface used to define <b>CRUD</b> operations with the rating table. <br>
 * It extends the {@link JpaRepository} interface delivered by Spring Data JPA.
 */

public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
