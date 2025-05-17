package be.kdg.swiftby.domain.bike;

import be.kdg.swiftby.domain.testEnv.BikeOwner;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "bike_ownerships")
public class BikeOwnership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private BikeInstance bike;

    @ManyToOne(optional = false)
    private BikeOwner owner;
}
