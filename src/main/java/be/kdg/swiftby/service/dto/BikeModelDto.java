package be.kdg.swiftby.service.dto;

import be.kdg.swiftby.domain.bike.BIKE_SIZE;
import be.kdg.swiftby.domain.bike.POWERTRAIN;

public record BikeModelDto(String brand,
                           String type,
                           POWERTRAIN powertrain,
                           BIKE_SIZE bikeSize,
                           int maxSupport,
                           MotorDto motor,
                           int batteryCapacity) {
}
