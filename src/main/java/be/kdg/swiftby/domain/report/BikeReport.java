package be.kdg.swiftby.domain.report;

import be.kdg.swiftby.domain.bike.Bike;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Entity
@Data
public class BikeReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate reportTime;
    private int mileage;
    private int assistanceLevel;
    private String technicianComment;
    @OneToOne
    @JoinColumn(name = "motor_data_id")
    private MotorData motorData;
    @OneToOne
    @JoinColumn(name = "wheel_data_id")
    private WheelData wheelData;
    @OneToOne
    @JoinColumn(name = "battery_data_id")
    private BatteryData batteryData;
    @OneToOne
    @JoinColumn(name = "pedal_data_id")
    private PedalData pedalData;
    @OneToOne
    @JoinColumn(name = "axial_sensor_data_id")
    private AxialSensorData axialSensorData;
    @OneToOne
    @JoinColumn(name = "test_bench_data")
    private TestBenchData testBenchData;
    @ManyToOne
    @JoinColumn(name = "bike_id")
    private Bike bike;
}
