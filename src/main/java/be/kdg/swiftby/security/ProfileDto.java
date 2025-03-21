package be.kdg.swiftby.security;

import be.kdg.swiftby.security.validation.PasswordMatches;
import be.kdg.swiftby.security.validation.ValidEmail;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@PasswordMatches
public class ProfileDto {
    @NonNull
    @NotEmpty
    @ValidEmail
    private String username;

    @NonNull
    @NotEmpty
    private String password;

    @NonNull
    @NotEmpty
    private String matchingPassword;

    @NonNull
    @NotEmpty
    private String firstName;
    @NonNull
    @NotEmpty
    private String lastName;

    @NonNull
    @NotEmpty
    private String phoneNumber;
    private Long facilityId;

}
