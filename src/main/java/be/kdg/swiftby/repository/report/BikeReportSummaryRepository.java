package be.kdg.swiftby.repository.report;

import be.kdg.swiftby.domain.report.BikeReportSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BikeReportSummaryRepository extends JpaRepository<BikeReportSummary, Long> {

    @Query("SELECT BikeReportSummary( " +
            "b, br.reportTime, AVG(br.mileage), AVG(br.assistanceLevel), " +
            "AVG(asd.horizontalInclination), AVG(asd.verticalInclination), " +
            "AVG(bd.current), AVG(bd.voltage), AVG(bd.capacity), AVG(bd.temperature), " +
            "AVG(md.enginePower), AVG(pd.torqueCrank), AVG(pd.cadence), " +
            "AVG(tbd.rollerTorque), AVG(tbd.loadCell), AVG(tbd.rol), AVG(wd.speed), AVG(wd.power), " +
            "COUNT(br.id)) " +
            "FROM BikeReport br " +
            "LEFT JOIN br.bike b " +
            "LEFT JOIN br.axialSensorData asd " +
            "LEFT JOIN br.batteryData bd " +
            "LEFT JOIN br.motorData md " +
            "LEFT JOIN br.pedalData pd " +
            "LEFT JOIN br.testBenchData tbd " +
            "LEFT JOIN br.wheelData wd " +
            "WHERE br.bike.id = :bikeId AND FUNCTION('DATE', br.reportTime) = :reportDate " +
            "GROUP BY b, br.reportTime")
    BikeReportSummary getBikeReportSummary(@Param("bikeId") Long bikeId, @Param("reportDate") LocalDate reportDate);

    List<BikeReportSummary> findAllByOrderByReportTimeDesc();
    @Query("SELECT s FROM BikeReportSummary s JOIN FETCH s.bike WHERE s.id = :id")
    Optional<BikeReportSummary> findByIdWithBike(@Param("id") Long id);



}
