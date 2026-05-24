package paybandhu.common.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.Documented;

@Documented
@Constraint(validatedBy = MobileNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface validMobileNumber {
}
