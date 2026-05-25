package paybandhu.common.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PanValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPan {

    String message() default"Invalid Pan number";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};


}
