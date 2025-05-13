package be.kdg.swiftby.domain.bike;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Motor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String engineType;
    @NonNull
    private String gearType;
    @NonNull
    private Integer maxPower;
    @NonNull
    private Integer nominalPower;
    @NonNull
    private Integer torque;
    @OneToMany(mappedBy = "motor")
    private Set<Bike> bikes;

}
