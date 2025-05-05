package be.kdg.swiftby.repository.report;

import be.kdg.swiftby.domain.report.MotorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotorDataRepository extends JpaRepository<MotorData, Long> {
}
