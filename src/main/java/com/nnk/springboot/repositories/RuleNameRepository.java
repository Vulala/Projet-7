package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface used to define <b>CRUD</b> operations with the rulename table. <br>
 * It extends the {@link JpaRepository} interface delivered by Spring Data JPA.
 */

public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {

}
