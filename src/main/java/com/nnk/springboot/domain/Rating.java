package com.nnk.springboot.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "rating")
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotBlank(message = "MoodysRating is mandatory")
	private String moodysRating;
	@NotBlank(message = "SandPRating is mandatory")
	private String sandRating;
	@NotBlank(message = "FitchRating is mandatory")
	private String fitchRating;
	@Digits(message = "Must be an integer", fraction = 1, integer = 12)
	private Integer orderNumber;

	public Rating() {
	}

	public Rating(Integer id, @NotBlank(message = "MoodysRating is mandatory") String moodysRating,
			@NotBlank(message = "SandRating is mandatory") String sandRating,
			@NotBlank(message = "FitchRating is mandatory") String fitchRating,
			@Digits(message = "Must be an integer", fraction = 1, integer = 12) Integer orderNumber) {
		this.id = id;
		this.moodysRating = moodysRating;
		this.sandRating = sandRating;
		this.fitchRating = fitchRating;
		this.orderNumber = orderNumber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMoodysRating() {
		return moodysRating;
	}

	public void setMoodysRating(String moodysRating) {
		this.moodysRating = moodysRating;
	}

	public String getSandRating() {
		return sandRating;
	}

	public void setSandRating(String sandRating) {
		this.sandRating = sandRating;
	}

	public String getFitchRating() {
		return fitchRating;
	}

	public void setFitchRating(String fitchRating) {
		this.fitchRating = fitchRating;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

}
