package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.*;
import lombok.*;

@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public abstract class Employee extends User {
    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;

    public Employee(String email, String firstName, String lastName, String phoneNumber) {
        super(email, firstName, lastName, phoneNumber);
    }

    public Employee() {

    }
}
