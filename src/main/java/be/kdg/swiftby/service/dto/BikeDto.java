package be.kdg.swiftby.service.dto;

import be.kdg.swiftby.domain.bike.BIKE_SIZE;
import be.kdg.swiftby.domain.bike.POWERTRAIN;

public record BikeDto(Long id, String brand, String type, String chassisNumber, POWERTRAIN powertrain, BIKE_SIZE bikeSize, int maxSupport,
                      MotorDto motorDto, int batteryCapacity) {
}
