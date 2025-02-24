package be.kdg.swiftby.domain.repository.report;

import be.kdg.swiftby.domain.report.TestBenchData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestBenchDataRepository extends JpaRepository<TestBenchData, Long> {
}
