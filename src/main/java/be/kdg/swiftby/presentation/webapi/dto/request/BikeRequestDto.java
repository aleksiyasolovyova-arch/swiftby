package be.kdg.swiftby.presentation.webapi.dto.request;

import be.kdg.swiftby.domain.bike.BIKE_SIZE;
import be.kdg.swiftby.domain.bike.POWERTRAIN;

public record BikeRequestDto(
        String brand,
        String type,
        String chassisNumber,
        POWERTRAIN powertrain,
        BIKE_SIZE bikeSize,
        Integer maxSupport,
        Integer batteryCapacity,
        MotorRequestDto motor
) {

}
