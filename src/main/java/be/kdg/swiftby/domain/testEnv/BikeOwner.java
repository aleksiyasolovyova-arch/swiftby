package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.Set;

@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class BikeOwner extends SBUser {

    // TODO: CREATE AN INTERMEDIATE CLASS FOR THIS AND NOT USE MANY_TO_MANY ANNOTATION
    @ManyToMany(mappedBy = "bikeOwners")
    private Set<Administrator> administrators;
}
