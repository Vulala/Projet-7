package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface used to define <b>CRUD</b> operations with the rating table. <br>
 * It extends the {@link JpaRepository} interface delivered by Spring Data JPA.
 */

public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
