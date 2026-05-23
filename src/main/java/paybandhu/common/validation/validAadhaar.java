package paybandhu.common.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AadhaarValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface validAadhaar  {

    String message() default "Invalid Aadhaar number";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
