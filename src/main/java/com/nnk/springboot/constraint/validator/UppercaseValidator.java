package com.nnk.springboot.constraint.validator;

import static com.nnk.springboot.validations.ValidationResult.SUCCESS;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.nnk.springboot.constraint.UppercaseConstraint;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.validations.UserValidation;
import com.nnk.springboot.validations.ValidationResult;

/**
 * 
 * {@link UppercaseValidator} define the @interface {@link UppercaseConstraint}.
 * <br>
 * Make use of the {@link UserValidation} @FunctionalInterface. <br>
 * Valid the password input to match at least 1 uppercase.
 */

public class UppercaseValidator implements ConstraintValidator<UppercaseConstraint, String> {

	@Override
	public void initialize(UppercaseConstraint password) {
	}

	@Override
	public boolean isValid(String passwordValidation, ConstraintValidatorContext context) {
		User user = new User();
		user.setPassword(passwordValidation);
		UserValidation userValidation = UserValidation.isPasswordContainingAtLeastOneUppercase();
		ValidationResult validationResult = userValidation.apply(user);
		return validationResult.compareTo(SUCCESS) == 0;

	}

}
