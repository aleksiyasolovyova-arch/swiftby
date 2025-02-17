package be.kdg.swiftby.domain.users;

import lombok.Data;

@Data
public abstract class SBUser {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
