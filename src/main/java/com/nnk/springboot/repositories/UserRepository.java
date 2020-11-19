package com.nnk.springboot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.nnk.springboot.domain.User;

/**
 * Interface used to define <b>CRUD</b> operations with the users table. <br>
 * It extends the {@link JpaRepository} interface delivered by Spring Data JPA.
 */

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

	Optional<User> findByUsername(String username);

}
