package paybandhu.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MobileNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface  ValidMobileNumber {

    String message() default "Invalid Mobile number";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};
}
