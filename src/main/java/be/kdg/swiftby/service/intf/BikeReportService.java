package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.report.BikeReport;
import be.kdg.swiftby.service.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface BikeReportService {
    List<BikeReport> getAll();
    BikeReport getById(Long id);
    BikeReport save(
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
    );
    void remove(Long id);
}
