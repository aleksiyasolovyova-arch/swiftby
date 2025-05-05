package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.*;
import java.util.Set;
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemAdmin extends SBUser {
    @OneToMany(mappedBy= "systemAdmin")
    private Set<Administrator> administrators;
}
