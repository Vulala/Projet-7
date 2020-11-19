package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface used to define <b>CRUD</b> operations with the bidlist table. <br>
 * It extends the {@link JpaRepository} interface delivered by Spring Data JPA.
 */

public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
