package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@RequiredArgsConstructor
public class Administrator extends Employee {
    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;
    @Column()
    private boolean isApproved;
    public Administrator(Facility facility, String email, String password, String firstName, String lastName, String phoneNumber) {
        this.setEmail(email);
        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPhoneNumber(phoneNumber);
    }

    @Override
    public boolean isLoginAllowed() {
        return this.isApproved;
    }

    }
