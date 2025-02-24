package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;
}
