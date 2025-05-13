package be.kdg.swiftby.presentation.viewmodels;

import be.kdg.swiftby.presentation.viewmodels.annotations.NotBlankIfPresent;
import be.kdg.swiftby.security.validation.ValidEmail;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EmployeeCreateViewModel {
    @ValidEmail
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String phoneNumber;

}
