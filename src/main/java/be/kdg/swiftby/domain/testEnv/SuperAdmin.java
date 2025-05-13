package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class SuperAdmin extends User {
    public SuperAdmin(String email, String password, String firstName, String lastName, String phoneNumber) {
        super();
    }
}
