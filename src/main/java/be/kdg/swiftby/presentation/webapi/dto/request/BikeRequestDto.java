package be.kdg.swiftby.presentation.webapi.dto.request;

import be.kdg.swiftby.domain.bike.BIKE_SIZE;
import be.kdg.swiftby.domain.bike.POWERTRAIN;
import be.kdg.swiftby.service.dto.BikeModelDto;

public record BikeRequestDto(
        String brand,
        String type,
        String chassisNumber,
        POWERTRAIN powertrain,
        BIKE_SIZE bikeSize,
        Integer maxSupport,
        Integer batteryCapacity,
        MotorRequestDto motor,
        Long ownerId
) {
    public BikeModelDto toBikeModelDto() {
        return new BikeModelDto(
                brand,
                type,
                powertrain,
                bikeSize,
                maxSupport,
                motor.toMotorDto(),
                batteryCapacity
        );
    }
}
