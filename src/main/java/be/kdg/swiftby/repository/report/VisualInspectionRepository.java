package be.kdg.swiftby.repository.report;

import be.kdg.swiftby.domain.report.VisualInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisualInspectionRepository extends JpaRepository<VisualInspection, Long> {
}
