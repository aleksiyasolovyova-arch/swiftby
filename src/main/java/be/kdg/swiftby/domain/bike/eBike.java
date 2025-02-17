package be.kdg.swiftby.domain.bike;

import lombok.Data;

@Data
public class eBike {
    private Long id;
    private String brand;
    private String type;
    private String chassisNumber;
    private String powertrainBrand;
    private BIKE_SIZE bikeSize;
    private int maxSupport;
    private Motor motor;
    private Battery battery;
    // don't exist in csv
    //    private int actualTorque;
    //    private double actualPower;

}
