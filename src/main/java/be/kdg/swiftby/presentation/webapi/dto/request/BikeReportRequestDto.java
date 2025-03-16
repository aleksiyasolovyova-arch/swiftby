package be.kdg.swiftby.presentation.webapi.dto.request;

import be.kdg.swiftby.presentation.webapi.dto.response.*;

import java.time.LocalDate;

public record BikeReportRequestDto(
        Long bikeId,
        LocalDate reportTime,
        int mileage,
        int assistanceLevel,
        String technicianComment,
        MotorDataApiResponseDto motorData,
        WheelDataApiResponseDto wheelData,
        BatteryDataApiResponseDto batteryData,
        PedalDataApiResponseDto pedalData,
        AxialSensorDataApiResponseDto axialSensorData,
        TestBenchDataApiResponseDto testBenchData
) {}
