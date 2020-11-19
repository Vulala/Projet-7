package com.nnk.springboot.validations;

/**
 * Enumeration used to define the return value of the {@link UserValidation}
 * Functional Interface.
 */

public enum ValidationResult {
	
	SUCCESS,
	MUST_CONTAIN_ATLEAST_EIGHT_CHARACTERS,
	MUST_CONTAIN_ATLEAST_ONE_DIGIT,
	MUST_CONTAIN_ATLEAST_ONE_SPECIAL_CHARACTER,
	MUST_CONTAIN_ATLEAST_ONE_UPPERCASE
	
}
