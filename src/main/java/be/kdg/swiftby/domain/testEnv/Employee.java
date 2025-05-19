package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.*;
import lombok.*;

//@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Employee extends User {
    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;

    @Column(name = "is_approved", nullable = false)
    private boolean isApproved;

    public Employee() {

    }

    public Employee(Facility facility, String email, String password, String firstName, String lastName, String phoneNumber) {
        super(email, password, firstName, lastName, phoneNumber);
        this.facility = facility;
    }

    public Employee(String email, String password, String firstName, String lastName, String phoneNumber) {
        super(email, password, firstName, lastName, phoneNumber);
    }


    @Override
    public boolean isLoginAllowed() {
        return this.isApproved;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
