package be.kdg.swiftby.presentation.viewmodels.annotations;

import be.kdg.swiftby.presentation.viewmodels.annotations.validators.NotBlankIfPresentValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = NotBlankIfPresentValidator.class)
@Target({ FIELD, METHOD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankIfPresent {

    String message() default "Field must not be blank if provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

