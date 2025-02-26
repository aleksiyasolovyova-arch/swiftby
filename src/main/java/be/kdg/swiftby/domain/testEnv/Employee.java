package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.*;
import lombok.*;

@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public abstract class Employee extends User {
    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;
}
