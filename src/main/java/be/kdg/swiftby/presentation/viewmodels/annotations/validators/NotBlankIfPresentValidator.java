package be.kdg.swiftby.presentation.viewmodels.annotations.validators;

import be.kdg.swiftby.presentation.viewmodels.annotations.NotBlankIfPresent;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankIfPresentValidator implements ConstraintValidator<NotBlankIfPresent, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null is allowed
        }
        return !value.trim().isEmpty(); // can't be blank
    }
}

