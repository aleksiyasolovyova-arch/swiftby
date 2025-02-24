package be.kdg.swiftby.service.dto;

public record BatteryDataDto(
        boolean chargeStatus,
        double current,
        double voltage,
        double capacity,
        double  temperature
) {
}
