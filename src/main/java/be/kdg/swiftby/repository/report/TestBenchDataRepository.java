package be.kdg.swiftby.repository.report;

import be.kdg.swiftby.domain.report.PedalData;
import be.kdg.swiftby.domain.report.TestBenchData;
import be.kdg.swiftby.domain.testEnv.TestBench;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestBenchDataRepository extends JpaRepository<TestBenchData, Long> {

}
