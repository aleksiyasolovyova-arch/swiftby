package be.kdg.swiftby.repository.report;

import be.kdg.swiftby.domain.report.BikeReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeReportRepository extends JpaRepository<BikeReport,Long> {
}
