package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@RequiredArgsConstructor
public class Administrator extends Employee {

    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;

    public Administrator(Facility facility, String email, String password, String firstName, String lastName, String phoneNumber) {
        this.facility = facility;
        this.setEmail(email);
        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPhoneNumber(phoneNumber);
    }


}
