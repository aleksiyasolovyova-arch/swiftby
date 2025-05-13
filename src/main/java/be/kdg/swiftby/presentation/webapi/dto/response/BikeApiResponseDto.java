package be.kdg.swiftby.presentation.webapi.dto.response;

import be.kdg.swiftby.domain.bike.BIKE_SIZE;
import be.kdg.swiftby.domain.bike.POWERTRAIN;

public record BikeApiResponseDto(
        Long id,
        String brand,
        String type,
        String chassisNumber,
        POWERTRAIN powertrain,
        BIKE_SIZE bikeSize,
        Integer maxSupport,
        Integer batteryCapacity
//        Long motorId
) {
}
