package be.kdg.swiftby.presentation.webapi.dto.response;

import be.kdg.swiftby.domain.bike.POWERTRAIN;
import be.kdg.swiftby.domain.bike.BIKE_SIZE;

public record BikeDto(
        Long id,
        String brand,
        String type,
        String chassisNumber,
        POWERTRAIN powertrain,
        BIKE_SIZE bikeSize,
        Integer maxSupport,
        Integer batteryCapacity
//        Long motorId
) {}
