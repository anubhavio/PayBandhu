package paybandhu.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AadhaarValidator implements ConstraintValidator<validAadhaar, String> {
    @Override
    public boolean isValid(String value
            , ConstraintValidatorContext constraintValidatorContext) {
        if(value == null){
            return false;
        }
        return value.matches("\\d{12}");
    }
}
