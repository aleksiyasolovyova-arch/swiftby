package be.kdg.swiftby.domain.bike;

import be.kdg.swiftby.domain.report.BikeReport;
import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.domain.testEnv.BikeOwner;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"reports","summaries"})
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull private String brand;
    @NonNull private String type;
    @NonNull private String chassisNumber;
    @NonNull private POWERTRAIN powertrain;
    @NonNull private BIKE_SIZE bikeSize;
    @NonNull private Integer maxSupport;
    @NonNull private Integer batteryCapacity;
    // CHEANGED THIS TO ONE -> MANY SINCE MULTIPLE BIKES CAN HAVE THE SAME MOTOR
    @ManyToOne
    @JoinColumn(name = "motor_id")
    @NonNull private Motor motor;

    // don't exist in csv
    //    private int actualTorque;
    //    private double actualPower;

    @OneToMany(mappedBy = "bike", fetch = FetchType.LAZY )
    private Set<BikeOwnership> ownerships;

    @OneToMany(mappedBy = "bike")
    private Set<BikeReport> reports;
    @OneToMany(mappedBy = "bike")
    private Set<BikeReportSummary> summaries;
}
