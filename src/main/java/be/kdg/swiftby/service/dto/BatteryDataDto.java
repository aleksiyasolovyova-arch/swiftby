package be.kdg.swiftby.service.dto;

public record BatteryDataDto(
        boolean chargeStatus,
        double batteryCurrent,
        double voltage,
        double capacity,
        double temperature
) {
}
