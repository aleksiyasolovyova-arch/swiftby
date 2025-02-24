package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Technician extends Employee{
    @ManyToOne
    @NonNull private Administrator administrator;
}
