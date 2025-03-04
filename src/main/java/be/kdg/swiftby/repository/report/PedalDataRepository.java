package be.kdg.swiftby.repository.report;

import be.kdg.swiftby.domain.report.PedalData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedalDataRepository extends JpaRepository<PedalData, Long> {
}
