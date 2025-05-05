package be.kdg.swiftby.repository.report;

import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.service.dto.data.BatteryTestDto;
import be.kdg.swiftby.service.dto.data.NominalLoadTestDto;
import be.kdg.swiftby.service.dto.data.TestProcedureOverviewDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BikeReportSummaryRepository extends JpaRepository<BikeReportSummary, Long> {


    @Query("""
    SELECT new be.kdg.swiftby.service.dto.data.TestProcedureOverviewDto(
        MAX(md.enginePower),
        m.maxPower,
        (CASE WHEN m.maxPower > 0 THEN ((MAX(md.enginePower) - m.maxPower) / m.maxPower) * 100 ELSE 0 END),

        MAX(tbd.rollerTorque),
        m.torque,
        (CASE WHEN m.torque > 0 THEN ((MAX(tbd.rollerTorque) - m.torque) / m.torque) * 100 ELSE 0 END),

        MAX(wd.power),
        m.nominalPower,
        (CASE WHEN m.nominalPower > 0 THEN ((MAX(wd.power) - m.nominalPower) / m.nominalPower) * 100 ELSE 0 END),

        b.maxSupport,
        (CASE WHEN MAX(md.enginePower) > 0 THEN ((b.maxSupport - (m.maxPower * 100.0 / MAX(md.enginePower)))) ELSE 0 END),

        100 - (
            (
                (CASE WHEN m.maxPower > 0 THEN ABS((MAX(md.enginePower) - m.maxPower) / m.maxPower * 100) ELSE 0 END) +
                (CASE WHEN m.torque > 0 THEN ABS((MAX(tbd.rollerTorque) - m.torque) / m.torque * 100) ELSE 0 END) +
                (CASE WHEN m.nominalPower > 0 THEN ABS((MAX(wd.power) - m.nominalPower) / m.nominalPower * 100) ELSE 0 END) +
                (CASE WHEN MAX(md.enginePower) > 0 THEN ABS((b.maxSupport - (m.maxPower * 100.0 / MAX(md.enginePower)))) ELSE 0 END)
            ) / 4
        )
    )
    FROM BikeReport br
    JOIN br.bike b
    JOIN b.motor m
    LEFT JOIN br.motorData md
    LEFT JOIN br.testBenchData tbd
    LEFT JOIN br.wheelData wd
    WHERE br.summary.id = :summaryId
    GROUP BY m.maxPower, m.torque, m.nominalPower, b.maxSupport
""")
    Optional<TestProcedureOverviewDto> getTestProcedureOverview(@Param("summaryId") Long summaryId);

    @Query("""
SELECT new be.kdg.swiftby.service.dto.data.BatteryTestDto(
    COALESCE(SUM(br.wheelData.power) / 3600.0, 0),
    b.batteryCapacity,
    CASE 
        WHEN b.batteryCapacity > 0 THEN (SUM(br.wheelData.power) / 3600.0) * 100.0 / b.batteryCapacity
        ELSE 0
    END,
    CASE 
        WHEN b.batteryCapacity > 0 THEN (SUM(br.wheelData.power) / 3600.0) * 100.0 / b.batteryCapacity
        ELSE 0
    END
)
FROM BikeReport br
JOIN br.bike b
JOIN br.summary s
WHERE s.id = :summaryId
  AND s.chargeStatus = true
  AND br.wheelData IS NOT NULL
GROUP BY b.batteryCapacity
""")
    Optional<BatteryTestDto> getBatteryTestData(@Param("summaryId") Long summaryId);


    List<BikeReportSummary> findAllByOrderByReportTimeDesc();
    @Query("SELECT s FROM BikeReportSummary s JOIN FETCH s.bike WHERE s.id = :id")
    Optional<BikeReportSummary> findByIdWithBike(@Param("id") Long id);
    List<BikeReportSummary> findByBikeIdOrderByReportTimeDesc(Long bikeId);



    @Query("SELECT s FROM BikeReportSummary s LEFT JOIN FETCH s.reports WHERE s.id = :id")
    Optional<BikeReportSummary> findByIdWithReports(@Param("id") Long id);



    @Query("SELECT s FROM BikeReportSummary s LEFT JOIN FETCH s.functionalityCheck WHERE s.id = :id")
    Optional<BikeReportSummary> findByIdWithCheck(@Param("id") Long id);




}
