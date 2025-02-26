package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Administrator extends Employee {


    // TODO: CREATE AN INTERMEDIATE CLASS FOR THIS AND NOT USE MANY_TO_MANY ANNOTATION
    @ManyToMany
    @JoinTable(
            name = "admin_bike_owner",
            joinColumns = @JoinColumn(name = "administrator_id"),
            inverseJoinColumns = @JoinColumn(name = "bike_owner_id")
    )
    private Set<BikeOwner> bikeOwners;

    @ManyToOne
    @NonNull
    private SuperAdmin superAdmin;

    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;
}
