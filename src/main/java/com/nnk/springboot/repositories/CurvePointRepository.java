package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface used to define <b>CRUD</b> operations with the curvepoint table.
 * <br>
 * It extends the {@link JpaRepository} interface delivered by Spring Data JPA.
 */

public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}
