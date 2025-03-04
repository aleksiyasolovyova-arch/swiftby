package be.kdg.swiftby.csv;

import be.kdg.swiftby.service.dto.*;

import java.time.LocalDate;

public record BikeReportCsvRecord(
        LocalDate reportTime,
        int mileage,
        int assistanceLevel,
        String technicianComment,
        AxialSensorDataDto axialSensorDataDto,
        BatteryDataDto batteryDataDto,
        MotorDataDto motorDataDto,
        PedalDataDto pedalDataDto,
        TestBenchDataDto testBenchDataDto,
        WheelDataDto wheelDataDto
) {

}
