package be.kdg.swiftby.repository.report;

import be.kdg.swiftby.domain.report.BatteryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatteryDataRepository extends JpaRepository<BatteryData,Long> {
}
