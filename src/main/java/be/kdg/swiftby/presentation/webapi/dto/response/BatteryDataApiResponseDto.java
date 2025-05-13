package be.kdg.swiftby.presentation.webapi.dto.response;

public record BatteryDataApiResponseDto(
        boolean chargeStatus,
        double current,
        double voltage,
        double capacity,
        double temperature
) {
}
