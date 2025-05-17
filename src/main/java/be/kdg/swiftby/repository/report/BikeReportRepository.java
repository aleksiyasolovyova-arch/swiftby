package be.kdg.swiftby.repository.report;

import be.kdg.swiftby.domain.report.BikeReport;
import be.kdg.swiftby.service.dto.BikeReportAggregationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeReportRepository extends JpaRepository<BikeReport, Long> {
    @Query("""
                SELECT br FROM BikeReport br
                LEFT JOIN FETCH br.bike b
                LEFT JOIN FETCH b.model
            """)
    List<BikeReport> getAllWithBikes();


    @Query("""
                SELECT new be.kdg.swiftby.service.dto.BikeReportAggregationDto(
                    r.bike.id,
                    MIN(r.reportTime),
                    AVG(r.mileage),
                    AVG(r.assistanceLevel),
                    AVG(ax.horizontalInclination),
                    AVG(ax.verticalInclination),
                    AVG(bat.batteryCurrent),
                    AVG(bat.voltage),
                    AVG(bat.capacity),
                    AVG(bat.temperature),
                    AVG(ped.torqueCrank),
                    AVG(ped.cadence),
                    AVG(test.rollerTorque),
                    AVG(test.loadCell),
                    AVG(test.rol),
                    AVG(wheel.speed),
                    AVG(wheel.power),
                    CASE WHEN SUM(CASE WHEN bat.chargeStatus = true THEN 1 ELSE 0 END) > 0 THEN true ELSE false END,
                    CASE WHEN SUM(CASE WHEN test.statusPlug = true THEN 1 ELSE 0 END) > 0 THEN true ELSE false END
                )
                FROM BikeReport r
                JOIN r.axialSensorData ax
                JOIN r.batteryData bat
                JOIN r.motorData mot
                JOIN r.pedalData ped
                JOIN r.testBenchData test
                JOIN r.wheelData wheel
                WHERE r.id IN :reportIds
                GROUP BY r.bike.id
            """)
    BikeReportAggregationDto aggregateReports(@Param("reportIds") List<Long> reportIds);

    List<BikeReport> findBySummaryId(Long summaryId);

    @Query("""
    SELECT br from BikeReport br
    LEFT JOIN FETCH br.bike b
    LEFT JOIN FETCH b.ownerships bos
    LEFT JOIN FETCH bos.owner bo
    WHERE bo.id = :bikeOwnerId
    """)
    List<BikeReport> findAllByBikeOwnerId(Long bikeOwnerId);
}
