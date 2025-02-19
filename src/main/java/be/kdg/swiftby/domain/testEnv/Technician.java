package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public class Technician extends Employee{
    @ManyToOne
    private Administrator administrator;
}
