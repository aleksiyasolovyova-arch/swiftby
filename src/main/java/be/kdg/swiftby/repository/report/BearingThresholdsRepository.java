package be.kdg.swiftby.repository.report;

import be.kdg.swiftby.domain.report.BearingThresholds;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BearingThresholdsRepository extends JpaRepository<BearingThresholds, Long> {
    Optional<BearingThresholds> findTopByOrderByIdDesc(); // last saved thresholds
}
