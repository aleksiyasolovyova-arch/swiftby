package be.kdg.swiftby.repository.report;

import be.kdg.swiftby.domain.report.AxialSensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AxialSensorDataRepository extends JpaRepository<AxialSensorData, Long> {
}
