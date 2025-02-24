package be.kdg.swiftby.repository.testEnvironment;

import be.kdg.swiftby.domain.testEnv.TestBench;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestBenchRepository extends JpaRepository<TestBench, Long> {
    List<TestBench> findAllByFacilityId(Long id);
    void removeAllByFacilityId(Long id);
}
