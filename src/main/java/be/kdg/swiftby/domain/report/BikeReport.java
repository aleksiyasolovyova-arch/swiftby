package be.kdg.swiftby.domain.report;

import be.kdg.swiftby.domain.bike.Bike;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class BikeReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime reportTime;
    private int mileage;
    private int assistanceLevel;
    private String technicianComment;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "motor_data_id")
    private MotorData motorData;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "wheel_data_id")
    private WheelData wheelData;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "battery_data_id")
    private BatteryData batteryData;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "pedal_data_id")
    private PedalData pedalData;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "axial_sensor_data_id")
    private AxialSensorData axialSensorData;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "test_bench_data")
    private TestBenchData testBenchData;
    @ManyToOne
    @JoinColumn(name = "bike_id")
    private Bike bike;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "summary_id")
    private BikeReportSummary summary;

    public BikeReport() {
    }


    public BikeReport(LocalDateTime reportTime, int mileage, int assistanceLevel, String technicianComment, AxialSensorData axialSensorData, BatteryData batteryData, MotorData motorData, PedalData pedalData, TestBenchData testBenchData, WheelData wheelData) {
        this.reportTime = reportTime;
        this.mileage = mileage;
        this.assistanceLevel = assistanceLevel;
        this.technicianComment = technicianComment;
        this.axialSensorData = axialSensorData;
        this.batteryData = batteryData;
        this.motorData = motorData;
        this.pedalData = pedalData;
        this.testBenchData = testBenchData;
        this.wheelData = wheelData;
    }
}
