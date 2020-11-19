package com.nnk.springboot.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "curvePoint")
public class CurvePoint {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull(message = "Must not be null")
	@Digits(message = "Must be a number", fraction = 1, integer = 12)
	@Min(message = "Must be atleast 1", value = 1)
	private Integer curveId;
	private Timestamp asOfDate;
	@Digits(message = "Must be a number", fraction = 1, integer = 12)
	private Double term;
	@Digits(message = "Must be a number", fraction = 1, integer = 12)
	private Double value;
	private Timestamp creationDate;

	public CurvePoint() {
	}

	public CurvePoint(Integer id,
			@NotNull(message = "Must not be null") @Digits(message = "Must be a number", fraction = 1, integer = 12) @Min(message = "Must be atleast 1", value = 1) Integer curveId,
			@Digits(message = "Must be a number", fraction = 1, integer = 12) Double term,
			@Digits(message = "Must be a number", fraction = 1, integer = 12) Double value) {
		this.id = id;
		this.curveId = curveId;
		this.term = term;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCurveId() {
		return curveId;
	}

	public void setCurveId(Integer curveId) {
		this.curveId = curveId;
	}

	public Timestamp getAsOfDate() {
		return asOfDate;
	}

	public void setAsOfDate(Timestamp asOfDate) {
		this.asOfDate = asOfDate;
	}

	public Double getTerm() {
		return term;
	}

	public void setTerm(Double term) {
		this.term = term;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

}
