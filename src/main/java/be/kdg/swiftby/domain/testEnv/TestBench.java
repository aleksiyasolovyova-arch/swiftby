package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class TestBench {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;
}
