package com.nnk.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.domain.Trade;

/**
 * Interface used to define <b>CRUD</b> operations with the trade table. <br>
 * It extends the {@link JpaRepository} interface delivered by Spring Data JPA.
 */

public interface TradeRepository extends JpaRepository<Trade, Integer> {

}
