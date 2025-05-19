package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.*;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Employee extends User {
    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;

    @Column(nullable = false)
    private boolean isApproved;


    public Employee(String email, String firstName, String lastName, String phoneNumber) {
        super(email, firstName, lastName, phoneNumber);
    }

    public Employee(Facility facility, String email, String password, String firstName, String lastName, String phoneNumber) {
        super(email, password, firstName, lastName, phoneNumber);
        this.facility = facility;
    }

    public Employee() {

    }

    public Employee(String email, String password, String firstName, String lastName, String phoneNumber) {
        super(email, password, firstName, lastName, phoneNumber);
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
