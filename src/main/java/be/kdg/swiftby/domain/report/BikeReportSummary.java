package be.kdg.swiftby.domain.report;

import be.kdg.swiftby.domain.bike.Bike;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

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
    private double current;
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


    public BikeReportSummary(Bike bike, LocalDate reportTime,
                             Double avgMileage, Double avgAssistanceLevel,
                             Double horizontalInclination, Double verticalInclination,
                             Double current, Double voltage, Double capacity, Double temperature,
                             Double enginePower, Double torqueCrank, Double cadence,
                             Double rollerTorque, Double loadCell, Double rol,
                             Double speed, Double power, String technicianComment) {  // ✅ Include technicianComment
        this.bike = bike;
        this.reportTime = reportTime;
        this.avgMileage = avgMileage;
        this.avgAssistanceLevel = avgAssistanceLevel;
        this.horizontalInclination = horizontalInclination;
        this.verticalInclination = verticalInclination;
        this.current = current;
        this.voltage = voltage;
        this.capacity = capacity;
        this.temperature = temperature;
        this.enginePower = enginePower;
        this.torqueCrank = torqueCrank;
        this.cadence = cadence;
        this.rollerTorque = rollerTorque;
        this.loadCell = loadCell;
        this.rol = rol;
        this.speed = speed;
        this.power = power;
        this.technicianComment = technicianComment;

    }



    public BikeReportSummary() {
    }


}
