package com.nnk.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.domain.RuleName;

/**
 * Interface used to define <b>CRUD</b> operations with the rulename table. <br>
 * It extends the {@link JpaRepository} interface delivered by Spring Data JPA.
 */

public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {

}
