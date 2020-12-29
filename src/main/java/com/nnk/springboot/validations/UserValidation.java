package com.nnk.springboot.validations;

import static com.nnk.springboot.validations.ValidationResult.MUST_CONTAIN_ATLEAST_EIGHT_CHARACTERS;
import static com.nnk.springboot.validations.ValidationResult.MUST_CONTAIN_ATLEAST_ONE_DIGIT;
import static com.nnk.springboot.validations.ValidationResult.MUST_CONTAIN_ATLEAST_ONE_SPECIAL_CHARACTER;
import static com.nnk.springboot.validations.ValidationResult.MUST_CONTAIN_ATLEAST_ONE_UPPERCASE;
import static com.nnk.springboot.validations.ValidationResult.SUCCESS;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nnk.springboot.domain.User;

/*
 * {@link @FunctionalInterface} used to proceed the validation of the user's password.
 * {@method passwordValidation} is used to chain the different functions.
 */

@FunctionalInterface
public interface UserValidation extends Function<User, ValidationResult> {

	default UserValidation passwordValidation(UserValidation otherValidation) {
		return user -> {
			ValidationResult result = this.apply(user);
			return result.compareTo(SUCCESS) == 0 ? SUCCESS : result;
		};
	}

	static UserValidation isPasswordContainingAtLeastEightCharacters() {
		return user -> functionalRegex("[a-zA-Z\\W\\d]{8,}", user.getPassword()) ? SUCCESS
				: MUST_CONTAIN_ATLEAST_EIGHT_CHARACTERS;
	}

	static UserValidation isPasswordContainingAtLeastOneDigit() {
		return user -> functionalRegex(".*\\d", user.getPassword()) ? SUCCESS : MUST_CONTAIN_ATLEAST_ONE_DIGIT;
	}

	static UserValidation isPasswordContainingAtLeastOneSpecialCharacter() {
		return user -> functionalRegex(".*\\W", user.getPassword()) ? SUCCESS
				: MUST_CONTAIN_ATLEAST_ONE_SPECIAL_CHARACTER;
	}

	static UserValidation isPasswordContainingAtLeastOneUppercase() {
		return user -> functionalRegex(".*[A-Z]", user.getPassword()) ? SUCCESS : MUST_CONTAIN_ATLEAST_ONE_UPPERCASE;
	}

	/**
	 * {@link functionalRegex} <br>
	 * Method used to use regex with functional programing.
	 * 
	 * @param regex    to confront to the :
	 * @param password
	 * @return true or false, depending of if the password match or not the provided
	 *         regex.
	 */
	static boolean functionalRegex(String regex, String password) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(password);
		return m.lookingAt();
	}
}
