package be.kdg.swiftby.repository.report;

import be.kdg.swiftby.domain.report.BikeReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BikeReportRepository extends JpaRepository<BikeReport,Long> {
    @Query("""
    SELECT br FROM BikeReport br
    LEFT JOIN FETCH br.bike
    """)
    List<BikeReport> getAllWithBikes();

}
