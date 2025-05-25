package be.kdg.swiftby.presentation.webapi.dto;

import be.kdg.swiftby.domain.bike.BIKE_SIZE;

public record BikeInstanceDto(
        Long id,
        String chassisNumber,
        Long modelId,
        String brand,
        String type,
        Integer batteryCapacity,
        BIKE_SIZE bikeSize
) {}
