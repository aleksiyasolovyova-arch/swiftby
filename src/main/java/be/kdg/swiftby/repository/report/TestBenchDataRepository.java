package be.kdg.swiftby.repository.report;

import be.kdg.swiftby.domain.report.PedalData;
import be.kdg.swiftby.domain.report.TestBenchData;
import be.kdg.swiftby.domain.testEnv.TestBench;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestBenchDataRepository extends JpaRepository<TestBenchData, Long> {

}
