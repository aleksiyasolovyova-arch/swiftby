package be.kdg.swiftby.presentation.webapi.dto;

public record BikeInstanceDto(
        Long id,
        String chassisNumber,
        Long modelId,
        String brand,
        String type
) {}
