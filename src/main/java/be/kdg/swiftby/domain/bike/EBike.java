package be.kdg.swiftby.domain.bike;

import be.kdg.swiftby.domain.report.BikeReport;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class EBike {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String brand;
    private String type;
    private String chassisNumber;
    private String powertrainBrand;
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

    @OneToMany(mappedBy = "eBike")
    private Set<BikeReport> reports;

}
