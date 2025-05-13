package be.kdg.swiftby.presentation.webapi.dto.response;

import java.time.LocalDateTime;

public record BikeReportApiResponseDto(
        Long id,
        LocalDateTime reportTime,
        int mileage,
        int assistanceLevel,
        String technicianComment,
        MotorDataApiResponseDto motorData,
        WheelDataApiResponseDto wheelData,
        BatteryDataApiResponseDto batteryData,
        PedalDataApiResponseDto pedalData,
        AxialSensorDataApiResponseDto axialSensorData,
        TestBenchDataApiResponseDto testBenchData,
        BikeApiResponseDto bike
) {
}
