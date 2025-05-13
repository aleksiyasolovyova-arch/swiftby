package be.kdg.swiftby.domain.testEnv;

import be.kdg.swiftby.domain.report.TestBenchData;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"testBenchData"})
public class TestBench {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "facility_id")
    @NonNull
    private Facility facility;
    @OneToMany(mappedBy = "testBench")
    private Set<TestBenchData> testBenchData;


}
