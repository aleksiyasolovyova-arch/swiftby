package be.kdg.swiftby.repository.report;

import be.kdg.swiftby.domain.report.WheelData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WheelDataRepository extends JpaRepository<WheelData, Long> {
}
