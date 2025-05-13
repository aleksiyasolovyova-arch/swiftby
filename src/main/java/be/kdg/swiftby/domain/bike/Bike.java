package be.kdg.swiftby.domain.bike;

import be.kdg.swiftby.domain.report.BikeReport;
import be.kdg.swiftby.domain.report.BikeReportSummary;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"reports", "summaries"})
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String brand;
    @NonNull
    private String type;
    @NonNull
    private String chassisNumber;
    @NonNull
    private POWERTRAIN powertrain;
    @NonNull
    private BIKE_SIZE bikeSize;
    @NonNull
    private Integer maxSupport;
    @NonNull
    private Integer batteryCapacity;
    // CHEANGED THIS TO ONE -> MANY SINCE MULTIPLE BIKES CAN HAVE THE SAME MOTOR
    @ManyToOne
    @JoinColumn(name = "motor_id")
    @NonNull
    private Motor motor;

    // don't exist in csv
    //    private int actualTorque;
    //    private double actualPower;

    @OneToMany(mappedBy = "bike", fetch = FetchType.LAZY)
    private Set<BikeOwnership> ownerships;

    @OneToMany(mappedBy = "bike")
    private Set<BikeReport> reports;
    @OneToMany(mappedBy = "bike")
    private Set<BikeReportSummary> summaries;

    public Bike(@NonNull String brand, @NonNull String type, @NonNull String chassisNumber, @NonNull POWERTRAIN powertrain, @NonNull BIKE_SIZE bikeSize, @NonNull Integer maxSupport, @NonNull Integer batteryCapacity, @NonNull Motor motor, Set<BikeOwnership> ownerships) {
        this.brand = brand;
        this.type = type;
        this.chassisNumber = chassisNumber;
        this.powertrain = powertrain;
        this.bikeSize = bikeSize;
        this.maxSupport = maxSupport;
        this.batteryCapacity = batteryCapacity;
        this.motor = motor;
        this.ownerships = ownerships;
    }
}
