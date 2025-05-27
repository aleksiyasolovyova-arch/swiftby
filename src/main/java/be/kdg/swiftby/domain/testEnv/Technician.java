// Technician.java
package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class Technician extends Employee {
    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;
    @Column()
    private boolean isApproved;

    public Technician(Facility facility, String email, String password, String firstName, String lastName, String phoneNumber) {
        super(facility, email, password, firstName, lastName, phoneNumber);
    }
    @Override
    public boolean isLoginAllowed() {
        return this.isApproved;
    }
}
