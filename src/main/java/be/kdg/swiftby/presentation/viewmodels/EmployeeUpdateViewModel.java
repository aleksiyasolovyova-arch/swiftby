package be.kdg.swiftby.presentation.viewmodels;

import be.kdg.swiftby.presentation.viewmodels.annotations.NotBlankIfPresent;
import be.kdg.swiftby.security.validation.ValidEmail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EmployeeUpdateViewModel {
    @ValidEmail
    @NotBlankIfPresent
    private String email;
    @NotBlankIfPresent
    private String password;
    @NotBlankIfPresent
    private String firstName;
    @NotBlankIfPresent
    private String lastName;
    @NotBlankIfPresent
    private String phoneNumber;
    private Long facilityId;

}
