package be.kdg.swiftby.domain.report;

import be.kdg.swiftby.domain.bike.Bike;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "bike_report_summary")
@AllArgsConstructor
public class BikeReportSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bike_id", nullable = false)
    private Bike bike;

    @Column(nullable = false)
    private LocalDate reportTime;

    private double avgMileage;
    private double avgAssistanceLevel;
    private double horizontalInclination;
    private double verticalInclination;
    private boolean chargeStatus;
    private double batteryCurrent;
    private double voltage;
    private double capacity;
    private double temperature;
    private String engineType;
    private String gearType;
    private Integer maxPower;
    private Integer nominalPower;
    private Integer torque;
    @Column(nullable = true)
    private Double enginePower;
    private double torqueCrank;
    private double cadence;
    private double rollerTorque;
    private double loadCell;
    private double rol;
    private int loadPower;
    private boolean statusPlug;
    private double speed;
    private double power;
    @Column(length = 1000)
    private String technicianComment;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "summary_id")
    private List<BikeReport> reports;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "functionality_check_id")
    private FunctionalityCheck functionalityCheck;

//    private long reportCount;
//
//    private double avgBatteryCurrent;
//    private double avgBatteryVoltage;
//    private double avgBatteryCapacity;
//    private double avgBatteryTemperature;
//
//    private double avgMotorPower;
//    private double avgWheelSpeed;

    public BikeReportSummary() {
    }


}
