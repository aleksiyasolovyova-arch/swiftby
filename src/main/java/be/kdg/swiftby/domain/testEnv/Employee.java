package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class Employee extends SBUser{
    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;
}
