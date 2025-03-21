package be.kdg.swiftby.security.validation;


import be.kdg.swiftby.security.ProfileDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        ProfileDto user = (ProfileDto) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
