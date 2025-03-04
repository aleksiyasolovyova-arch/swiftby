package be.kdg.swiftby.presentation.webapi.dto.response;

public record BatteryDataDto(
    boolean chargeStatus,
    double current,
    double voltage,
    double capacity,
    double temperature
) {}
