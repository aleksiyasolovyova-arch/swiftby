package be.kdg.swiftby.presentation.webapi.dto.request;

import be.kdg.swiftby.presentation.webapi.dto.response.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record BikeReportRequestDto(
        Long bikeId,
        LocalDateTime reportTime,
        int mileage,
        int assistanceLevel,
        String technicianComment,
        MotorDataApiResponseDto motorData,
        WheelDataApiResponseDto wheelData,
        BatteryDataApiResponseDto batteryData,
        PedalDataApiResponseDto pedalData,
        AxialSensorDataApiResponseDto axialSensorData,
        TestBenchDataApiResponseDto testBenchData
) {

}
