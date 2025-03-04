package be.kdg.swiftby.presentation.webapi.dto.response;

import java.time.LocalDate;

public record BikeReportDto(
        Long id,
        LocalDate reportTime,
        int mileage,
        int assistanceLevel,
        String technicianComment,
        MotorDataDto motorData,
        WheelDataDto wheelData,
        BatteryDataDto batteryData,
        PedalDataDto pedalData,
        AxialSensorDataDto axialSensorData,
        TestBenchDataDto testBenchData,
        BikeDto bike
) {}
