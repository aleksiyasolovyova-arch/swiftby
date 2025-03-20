package be.kdg.swiftby.repository.report;

import be.kdg.swiftby.domain.report.BikeReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BikeReportRepository extends JpaRepository<BikeReport,Long> {
    @Query("""
    SELECT br FROM BikeReport br
    LEFT JOIN FETCH br.bike
    """)
    List<BikeReport> getAllWithBikes();


    @Query(value = """
    SELECT br.bike_id, br.report_time,
           AVG(br.mileage) AS total_mileage,
           AVG(br.assistance_level) AS avg_assistance_level,
           COUNT(br.id) AS report_count,
           AVG(bd.current) AS total_battery_current,
           AVG(bd.voltage) AS total_battery_voltage,
           AVG(bd.capacity) AS total_battery_capacity,
           AVG(bd.temperature) AS avg_battery_temperature,
           AVG(md.engine_power) AS total_motor_power,
           AVG(wd.speed) AS total_wheel_speed
    FROM bike_report br
    LEFT JOIN battery_data bd ON br.battery_data_id = bd.id
    LEFT JOIN motor_data md ON br.motor_data_id = md.id
    LEFT JOIN wheel_data wd ON br.wheel_data_id = wd.id
    WHERE br.bike_id = :bikeId AND br.report_time = :reportDate
    GROUP BY br.bike_id, br.report_time
""", nativeQuery = true)
    Optional<?> getAggregatedBikeReport(@Param("bikeId") Long bikeId, @Param("reportDate") LocalDate reportDate);
}




