package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class Employee extends SBUser{
}
