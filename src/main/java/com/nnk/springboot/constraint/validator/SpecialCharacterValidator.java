package com.nnk.springboot.constraint.validator;

import static com.nnk.springboot.validations.ValidationResult.SUCCESS;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.nnk.springboot.constraint.SpecialCharacterConstraint;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.validations.UserValidation;
import com.nnk.springboot.validations.ValidationResult;

/**
 * 
 * {@link SpecialCharacterValidator} define the @interface
 * {@link SpecialCharacterConstraint}. <br>
 * Make use of the {@link UserValidation} @FunctionalInterface. <br>
 * Valid the password input to match at least 1 special character.
 */

public class SpecialCharacterValidator implements ConstraintValidator<SpecialCharacterConstraint, String> {

	@Override
	public void initialize(SpecialCharacterConstraint password) {
	}

	@Override
	public boolean isValid(String passwordValidation, ConstraintValidatorContext context) {
		User user = new User();
		user.setPassword(passwordValidation);
		UserValidation userValidation = UserValidation.isPasswordContainingAtLeastOneSpecialCharacter();
		ValidationResult validationResult = userValidation.apply(user);
		return validationResult.compareTo(SUCCESS) == 0;

	}

}
