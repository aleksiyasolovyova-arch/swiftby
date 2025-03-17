package be.kdg.swiftby.presentation.webapi.dto.request;

import be.kdg.swiftby.domain.bike.BIKE_SIZE;
import be.kdg.swiftby.domain.bike.POWERTRAIN;
import lombok.Data;

@Data
public class StartTestRequestDto {
    // bike Owner Information
    private String ownerEmail;
    private String ownerFirstName;
    private String ownerLastName;
    private String ownerPhoneNumber;

    // bike Information
    private String brand;
    private String type;
    private String chassisNumber;
    private BIKE_SIZE bikeSize;
    private POWERTRAIN powertrain;
    private Integer maxSupport;
    private Integer batteryCapacity;
    private String engineType;
    private String gearType;
    private Integer maxPower;
    private Integer nominalPower;
    private Integer torque;
    private Integer mileage;
    private Integer productionDate;

    // Test Setup
    private String testType;
    private Integer testBenchNumber;

}
