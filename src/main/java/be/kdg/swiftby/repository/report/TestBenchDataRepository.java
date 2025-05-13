package be.kdg.swiftby.repository.report;

import be.kdg.swiftby.domain.report.TestBenchData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestBenchDataRepository extends JpaRepository<TestBenchData, Long> {

}
