package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public class Customer extends SBUser{
    // TODO: CREATE AN INTERMEDIATE CLASS FOR THIS AND NOT USE MANY_TO_MANY ANNOTATION
    @ManyToMany(mappedBy = "customers")
    private Set<Administrator> administrators;

}
