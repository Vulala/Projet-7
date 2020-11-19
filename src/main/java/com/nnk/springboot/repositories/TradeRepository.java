package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface used to define <b>CRUD</b> operations with the trade table. <br>
 * It extends the {@link JpaRepository} interface delivered by Spring Data JPA.
 */

public interface TradeRepository extends JpaRepository<Trade, Integer> {

}
