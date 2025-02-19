package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.*;
import lombok.Data;
@MappedSuperclass
@Data
public abstract class SBUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
