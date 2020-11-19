package com.nnk.springboot.constraint.validator;

import static com.nnk.springboot.validations.ValidationResult.SUCCESS;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.nnk.springboot.constraint.DigitConstraint;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.validations.UserValidation;
import com.nnk.springboot.validations.ValidationResult;

/**
 * 
 * {@link DigitValidator} define the @interface {@link DigitConstraint}. <br>
 * Make use of the {@link UserValidation} @FunctionalInterface. <br>
 * Valid the password input to match at least 1 digit.
 */

public class DigitValidator implements ConstraintValidator<DigitConstraint, String> {

	@Override
	public void initialize(DigitConstraint password) {
	}

	@Override
	public boolean isValid(String passwordValidation, ConstraintValidatorContext context) {
		User user = new User();
		user.setPassword(passwordValidation);
		UserValidation userValidation = UserValidation.isPasswordContainingAtLeastOneDigit();
		ValidationResult validationResult = userValidation.apply(user);
		return validationResult.compareTo(SUCCESS) == 0;

	}

}
