package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class Employee extends SBUser{
    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;
    @Column(columnDefinition = "bit default 0")
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
