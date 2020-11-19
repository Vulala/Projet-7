package com.nnk.springboot.constraint.validator;

import static com.nnk.springboot.validations.ValidationResult.SUCCESS;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.nnk.springboot.constraint.CharactersConstraint;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.validations.UserValidation;
import com.nnk.springboot.validations.ValidationResult;

/**
 * 
 * {@link CharactersValidator} define the @interface
 * {@link CharactersConstraint}. <br>
 * Make use of the {@link UserValidation} @FunctionalInterface. <br>
 * Valid the password input to match at least 8 characters.
 */

public class CharactersValidator implements ConstraintValidator<CharactersConstraint, String> {

	@Override
	public void initialize(CharactersConstraint password) {
	}

	@Override
	public boolean isValid(String passwordValidation, ConstraintValidatorContext context) {
		User user = new User();
		user.setPassword(passwordValidation);
		UserValidation userValidation = UserValidation.isPasswordContainingAtLeastEightCharacters();
		ValidationResult validationResult = userValidation.apply(user);
		return validationResult.compareTo(SUCCESS) == 0;

	}

}
