package paybandhu.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AgentCodeValidator implements ConstraintValidator<ValidAgentCode, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null){
            return false;
        }
        return value.matches("^[A-Z]{4}[0-9]{5}$");
    }
}
