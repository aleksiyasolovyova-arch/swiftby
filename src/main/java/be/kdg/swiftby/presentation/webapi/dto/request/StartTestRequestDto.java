package be.kdg.swiftby.presentation.webapi.dto.request;

import be.kdg.swiftby.domain.bike.BIKE_SIZE;
import be.kdg.swiftby.domain.bike.POWERTRAIN;
import be.kdg.swiftby.service.TestType;
import lombok.Data;

@Data
public class StartTestRequestDto {

    // OWNER IS NOW SAVED SEPARATELY

//    private String ownerEmail;
//    private String ownerFirstName;
//    private String ownerLastName;
//    private String ownerPhoneNumber;

    // BAKE IS NOW SAVED SEPARATELY

//    private String brand;
//    private String type;
//    private String chassisNumber;
//    private BIKE_SIZE bikeSize;
//    private POWERTRAIN powertrain;
//    private String engineType;
//    private String gearType;
//    private Integer mileage;
//    private Integer productionDate;

    //
    private TestType testType;
    private String testBenchNumber;
//    private Integer batteryCapacity;
//    private Integer maxSupport;
//    private Integer maxPower;
//    private Integer nominalPower;
//    private Integer torque;

    // BIKE ID WILL BE USED TO RETRIEVE BIKE TOGETHER WITH ITS OWNER
    private Long bikeId;
    private Long checkId;
}
