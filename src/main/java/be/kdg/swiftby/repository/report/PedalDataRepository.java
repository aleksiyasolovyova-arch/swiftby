package be.kdg.swiftby.repository.report;

import be.kdg.swiftby.domain.report.PedalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedalDataRepository extends JpaRepository<PedalData, Long> {
}
