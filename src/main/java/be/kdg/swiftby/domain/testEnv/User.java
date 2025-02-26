package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.*;
import lombok.*;

@MappedSuperclass
@Getter
@Setter
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull private String email;
    //encrypted
    @NonNull private String password;
    @NonNull private String firstName;
    @NonNull private String lastName;
    @NonNull private String phoneNumber;
}
