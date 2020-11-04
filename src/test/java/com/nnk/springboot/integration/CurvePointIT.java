package com.nnk.springboot.integration;

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
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CurvePointIT {

	@Autowired
	private CurvePointRepository curvePointRepository;

	private CurvePoint curvePoint;

	@DisplayName("Initialize a curvePoint")
	@BeforeEach
	public void initialization() {
		curvePoint = new CurvePoint(1, 10, 10d, 30d);
	}

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	public void injectedComponentsAreRightlySetup() {
		assertThat(curvePointRepository).isNotNull();
	}

	@DisplayName("Find by id : curvePoint")
	@Test
	public void givenGettingACurvePoint_whenFindById_thenItReturnTheRightCurvePoint() {
		Optional<CurvePoint> result = curvePointRepository.findById(curvePoint.getId());
		assertTrue(result.isPresent());
	}

	@DisplayName("Find all : curvePoint")
	@Test
	public void givenGettingAllCurvePoint_whenFindAll_thenItReturnAllTheCurvePoints() {
		List<CurvePoint> listResult = curvePointRepository.findAll();
		assertTrue(listResult.size() > 0);
	}

	@DisplayName("Save : curvePoint")
	@Test
	public void givenSavingACurvePoint_whenSave_thenItSaveTheCurvePointInTheDB() {
		curvePoint = curvePointRepository.save(curvePoint);
		assertNotNull(curvePointRepository.findById(curvePoint.getId()));
		assertEquals(curvePointRepository.findById(curvePoint.getId()).get().getCurveId(), 10);
	}

	@DisplayName("Update : curvePoint")
	@Test
	public void givenUpdatingACurvePoint_whenUpdate_thenItUpdateTheCurvePointInTheDB() {
		curvePoint.setCurveId(20);
		curvePoint = curvePointRepository.save(curvePoint);
		assertNotNull(curvePointRepository.findById(curvePoint.getId()));
		assertEquals(curvePointRepository.findById(curvePoint.getId()).get().getCurveId(), 20);
	}

	@DisplayName("Delete : curvePoint")
	@Test
	public void givenDeletingACurvePoint_whenDelete_thenItDeleteTheCurvePointInTheDB() {
		curvePointRepository.delete(curvePoint);
		assertFalse(curvePointRepository.findById(curvePoint.getId()).isPresent());
	}
}
