package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.*;

import java.util.Set;
@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class SuperAdmin extends SBUser {
    @OneToMany(mappedBy= "systemAdmin")
    private Set<Administrator> administrators;
}
