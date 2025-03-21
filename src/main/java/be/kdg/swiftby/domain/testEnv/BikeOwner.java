package be.kdg.swiftby.domain.testEnv;

import be.kdg.swiftby.domain.bike.Bike;
import be.kdg.swiftby.domain.report.BikeReport;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Set;

@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class BikeOwner extends User {

    // TODO: CREATE AN INTERMEDIATE CLASS FOR THIS AND NOT USE MANY_TO_MANY ANNOTATION
    @ManyToMany(mappedBy = "bikeOwners")
    private Set<Administrator> administrators;

    @OneToMany(mappedBy = "bikeOwner")
    private Set<Bike> bikes;

    public BikeOwner(String email, String password, String firstName, String lastName, String phoneNumber) {
        super();
    }
    public BikeOwner(String email, String firstName, String lastName, String phoneNumber) {
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPhoneNumber(phoneNumber);
    }


}
