package be.kdg.swiftby.domain.testEnv;

import be.kdg.swiftby.domain.bike.Bike;
import be.kdg.swiftby.domain.bike.BikeOwnership;
import be.kdg.swiftby.domain.report.BikeReport;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id")
    private Facility facility;

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
