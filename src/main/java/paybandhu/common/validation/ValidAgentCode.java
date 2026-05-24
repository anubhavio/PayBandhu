package paybandhu.common.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AgentCodeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAgentCode {

    String message() default "Enter valid Agent_Code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
