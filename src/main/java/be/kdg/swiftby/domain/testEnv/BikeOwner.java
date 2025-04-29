package be.kdg.swiftby.domain.testEnv;

import be.kdg.swiftby.domain.bike.Bike;
import be.kdg.swiftby.domain.bike.BikeOwnership;
import be.kdg.swiftby.domain.report.BikeReport;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY )
    private Set<BikeOwnership> ownerships;
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
