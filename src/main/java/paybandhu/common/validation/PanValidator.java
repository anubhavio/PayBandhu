package paybandhu.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;

public class PanValidator implements ConstraintValidator<ValidPan, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.matches("^A-Z]{5}[0-9]{4}[A-Z]{1}$");
    }


}
