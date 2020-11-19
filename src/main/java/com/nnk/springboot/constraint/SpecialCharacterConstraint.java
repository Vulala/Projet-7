package com.nnk.springboot.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.nnk.springboot.constraint.validator.SpecialCharacterValidator;

/**
 * @interface used to define a constraint annotation, mainly used for the
 *            password validation.
 */

@Documented
@Constraint(validatedBy = SpecialCharacterValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SpecialCharacterConstraint {

	String message() default "The password provided is not valid: "
			+ "The password must contain atleast 1 special character.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}