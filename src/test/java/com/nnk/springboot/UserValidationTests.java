package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.validations.UserValidation;
import com.nnk.springboot.validations.ValidationResult;
import static com.nnk.springboot.validations.ValidationResult.*;

public class UserValidationTests {

	private User valid = new User(0, "", "P@ssword1", "", "");
	private User notValid = new User(0, "", "pass", "", "");

	@DisplayName("The password is valid")
	@Test
	public void givenAnUser_whenPasswordValidation_thenItIsValid() {
		UserValidation passwordValidation = UserValidation.isPasswordContainingAtLeastEightCharacters()
				.passwordValidation(UserValidation.isPasswordContainingAtLeastOneDigit())
				.passwordValidation(UserValidation.isPasswordContainingAtLeastOneSpecialCharacter())
				.passwordValidation(UserValidation.isPasswordContainingAtLeastOneUppercase());
		ValidationResult validationResult = passwordValidation.apply(valid);
		assertEquals(validationResult.compareTo(SUCCESS), 0); // Same assertion as below
		assertEquals(validationResult, SUCCESS);
	}

	@DisplayName("The password is not valid")
	@Test
	public void givenAnUser_whenPasswordValidation_thenItIsNotValid() {
		UserValidation passwordValidation = UserValidation.isPasswordContainingAtLeastEightCharacters()
				.passwordValidation(UserValidation.isPasswordContainingAtLeastOneDigit())
				.passwordValidation(UserValidation.isPasswordContainingAtLeastOneSpecialCharacter())
				.passwordValidation(UserValidation.isPasswordContainingAtLeastOneUppercase());
		ValidationResult validationResult = passwordValidation.apply(notValid);
		assertNotEquals(validationResult.compareTo(SUCCESS), 0); // Same assertion as below
		assertNotEquals(validationResult, SUCCESS);
	}

	@DisplayName("The password contain at least eight characters : valid")
	@Test
	public void givenAnUser_whenIsPasswordContainingAtLeastEightCharacters_thenItIsValid() {
		UserValidation isPasswordContainingAtLeastEightCharacters = UserValidation
				.isPasswordContainingAtLeastEightCharacters();
		ValidationResult validationResult = isPasswordContainingAtLeastEightCharacters.apply(valid);
		assertEquals(validationResult.compareTo(SUCCESS), 0);
	}

	@DisplayName("The password contain at least eight characters : not valid")
	@Test
	public void givenAnUser_whenIsPasswordContainingAtLeastEightCharacters_thenItIsNotValid() {
		UserValidation isPasswordContainingAtLeastEightCharacters = UserValidation
				.isPasswordContainingAtLeastEightCharacters();
		ValidationResult validationResult = isPasswordContainingAtLeastEightCharacters.apply(notValid);
		assertEquals(validationResult.compareTo(MUST_CONTAIN_ATLEAST_EIGHT_CHARACTERS), 0);
	}

	@DisplayName("The password contain at least one digit : valid")
	@Test
	public void givenAnUser_whenIsPasswordContainingAtLeastOneDigit_thenItIsValid() {
		UserValidation isPasswordContainingAtLeastOneDigit = UserValidation.isPasswordContainingAtLeastOneDigit();
		ValidationResult validationResult = isPasswordContainingAtLeastOneDigit.apply(valid);
		assertEquals(validationResult.compareTo(SUCCESS), 0);
	}

	@DisplayName("The password contain at least one digit : not valid")
	@Test
	public void givenAnUser_whenIsPasswordContainingAtLeastOneDigit_thenItIsNotValid() {
		UserValidation isPasswordContainingAtLeastOneDigit = UserValidation.isPasswordContainingAtLeastOneDigit();
		ValidationResult validationResult = isPasswordContainingAtLeastOneDigit.apply(notValid);
		assertEquals(validationResult.compareTo(MUST_CONTAIN_ATLEAST_ONE_DIGIT), 0);
	}

	@DisplayName("The password contain at least one special character : valid")
	@Test
	public void givenAnUser_whenIsPasswordContainingAtLeastOneSpecialCharacter_thenItIsValid() {
		UserValidation isPasswordContainingAtLeastOneSpecialCharacter = UserValidation
				.isPasswordContainingAtLeastOneSpecialCharacter();
		ValidationResult validationResult = isPasswordContainingAtLeastOneSpecialCharacter.apply(valid);
		assertEquals(validationResult.compareTo(SUCCESS), 0);
	}

	@DisplayName("The password contain at least one special character : not valid")
	@Test
	public void givenAnUser_whenIsPasswordContainingAtLeastOneSpecialCharacter_thenItIsNotValid() {
		UserValidation isPasswordContainingAtLeastOneSpecialCharacter = UserValidation
				.isPasswordContainingAtLeastOneSpecialCharacter();
		ValidationResult validationResult = isPasswordContainingAtLeastOneSpecialCharacter.apply(notValid);
		assertEquals(validationResult.compareTo(MUST_CONTAIN_ATLEAST_ONE_SPECIAL_CHARACTER), 0);
	}

	@DisplayName("The password contain at least one uppercase : valid")
	@Test
	public void givenAnUser_whenIsPasswordContainingAtLeastOneUppercase_thenItIsValid() {
		UserValidation isPasswordContainingAtLeastOneUppercase = UserValidation
				.isPasswordContainingAtLeastOneUppercase();
		ValidationResult validationResult = isPasswordContainingAtLeastOneUppercase.apply(valid);
		assertEquals(validationResult.compareTo(SUCCESS), 0);
	}

	@DisplayName("The password contain at least one uppercase : not valid")
	@Test
	public void givenAnUser_whenIsPasswordContainingAtLeastOneUppercase_thenItIsNotValid() {
		UserValidation isPasswordContainingAtLeastOneUppercase = UserValidation
				.isPasswordContainingAtLeastOneUppercase();
		ValidationResult validationResult = isPasswordContainingAtLeastOneUppercase.apply(notValid);
		assertEquals(validationResult.compareTo(MUST_CONTAIN_ATLEAST_ONE_UPPERCASE), 0);
	}

}
