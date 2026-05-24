package paybandhu.common.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.Documented;

@Documented
@Constraint(validatedBy = MobileNumberValidator.class)
public @interface validMobileNumber {
}
