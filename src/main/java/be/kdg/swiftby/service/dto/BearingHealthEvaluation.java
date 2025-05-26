package be.kdg.swiftby.service.dto;


public record BearingHealthEvaluation(
        double horizontalRange,
        double verticalRange,
        boolean isBad
) {}

