package be.kdg.swiftby.domain.bike;

import be.kdg.swiftby.domain.report.BikeReport;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String type;
    private String chassisNumber;
    private POWERTRAIN powertrain;
    private BIKE_SIZE bikeSize;
    private int maxSupport;
    @OneToOne
    @JoinColumn(name = "motor_id")
    private Motor motor;

    @OneToOne
    @JoinColumn(name = "battery_id")
    private Battery battery;
    // don't exist in csv
    //    private int actualTorque;
    //    private double actualPower;

    @OneToMany(mappedBy = "bike")
    private Set<BikeReport> reports;

}
