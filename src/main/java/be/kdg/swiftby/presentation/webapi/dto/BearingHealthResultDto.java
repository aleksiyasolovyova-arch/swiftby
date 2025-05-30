package be.kdg.swiftby.presentation.webapi.dto;

public record BearingHealthResultDto(
        double horizontalRange,
        double verticalRange,
        double horizontalThreshold,
        double verticalThreshold,
        String result
) {}


