package paybandhu.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class MobileNumberValidator implements ConstraintValidator<ValidMobileNumber, String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return false;
        }

        return value.matches("^[6-9]\\d{9}$");
    }
}
