package be.kdg.swiftby.domain.testEnv;

import be.kdg.swiftby.domain.report.TestBenchData;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class TestBench {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;
    @OneToMany(mappedBy = "testBench")
    private Set<TestBenchData> testBenchData;
}
