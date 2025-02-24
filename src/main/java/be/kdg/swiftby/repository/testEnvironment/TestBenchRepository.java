package be.kdg.swiftby.repository.testEnvironment;

import be.kdg.swiftby.domain.testEnv.TestBench;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestBenchRepository extends JpaRepository<TestBench, Long> {
}
