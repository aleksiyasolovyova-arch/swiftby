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

            bm.maxSupport,
            (CASE WHEN MAX(md.enginePower) > 0 THEN ((bm.maxSupport - (m.maxPower * 100.0 / MAX(md.enginePower)))) ELSE 0 END),

            100 - (
                (
                    (CASE WHEN m.maxPower > 0 THEN ABS((MAX(md.enginePower) - m.maxPower) / m.maxPower * 100) ELSE 0 END) +
                    (CASE WHEN m.torque > 0 THEN ABS((MAX(tbd.rollerTorque) - m.torque) / m.torque * 100) ELSE 0 END) +
                    (CASE WHEN m.nominalPower > 0 THEN ABS((MAX(wd.power) - m.nominalPower) / m.nominalPower * 100) ELSE 0 END) +
                    (CASE WHEN MAX(md.enginePower) > 0 THEN ABS((bm.maxSupport - (m.maxPower * 100.0 / MAX(md.enginePower)))) ELSE 0 END)
                ) / 4
            )
        )
        FROM BikeReport br
        JOIN br.bike.model bm
        JOIN bm.motor m
        LEFT JOIN br.motorData md
        LEFT JOIN br.testBenchData tbd
        LEFT JOIN br.wheelData wd
        WHERE br.summary.id = :summaryId
        GROUP BY m.maxPower, m.torque, m.nominalPower, bm.maxSupport
    """)
    Optional<TestProcedureOverviewDto> getTestProcedureOverview(@Param("summaryId") Long summaryId);

    @Query("""
    SELECT new be.kdg.swiftby.service.dto.data.BatteryTestDto(
        COALESCE(SUM(br.wheelData.power) / 3600.0, 0),
        bm.batteryCapacity,
        CASE WHEN bm.batteryCapacity > 0 THEN 
            (SUM(br.wheelData.power) / 3600.0) * 100.0 / bm.batteryCapacity 
        ELSE 0 END,
        CASE WHEN bm.batteryCapacity > 0 THEN 
            ROUND((SUM(br.wheelData.power) / 3600.0) * 100.0 / bm.batteryCapacity) 
        ELSE 0 END
    )
    FROM BikeReport br
    JOIN br.bike.model bm
    JOIN br.summary s
    WHERE s.id = :summaryId
      AND br.wheelData IS NOT NULL
    GROUP BY bm.batteryCapacity
""")
    Optional<BatteryTestDto> getBatteryTestData(@Param("summaryId") Long summaryId);

    List<BikeReportSummary> findAllByOrderByReportTimeDesc();

    @Query("SELECT s FROM BikeReportSummary s JOIN FETCH s.bikeInstance WHERE s.id = :id")
    Optional<BikeReportSummary> findByIdWithBike(@Param("id") Long id);

    List<BikeReportSummary> findByBikeInstanceIdOrderByReportTimeDesc(Long bikeInstanceId);

    @Query("SELECT s FROM BikeReportSummary s LEFT JOIN FETCH s.reports WHERE s.id = :id")
    Optional<BikeReportSummary> findByIdWithReports(@Param("id") Long id);

    @Query("SELECT s FROM BikeReportSummary s LEFT JOIN FETCH s.functionalityCheck WHERE s.id = :id")
    Optional<BikeReportSummary> findByIdWithCheck(@Param("id") Long id);

    List<BikeReportSummary> findAllByBikeInstanceId(Long bikeInstanceId);

    @Query("""
    SELECT brs FROM BikeReportSummary brs
    LEFT JOIN FETCH brs.bikeInstance bi
    LEFT JOIN FETCH bi.ownerships bos
    LEFT JOIN FETCH bos.owner bo
    WHERE bo.id = :bikeOwnerId
    """)
    List<BikeReportSummary> findAllBikeReportSummariesByBikeOwnerId(Long bikeOwnerId);
}
