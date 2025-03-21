package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.report.BikeReport;
import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.service.dto.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BikeReportService {
    List<BikeReport> getAll();
    List<BikeReport> getAllWithBikes();
    BikeReport getById(Long id);
    BikeReport save(
            Long bikeId,
            LocalDateTime reportTime,
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
    List<BikeReport> getReportsBySummaryId(Long summaryId);
    BikeReport aggregatedReport(Long reportId);
    BikeReportSummary saveReportSummary(Long bikeId, LocalDate reportDate);
    BikeReportSummary saveReportSummaryFromSavedReports(List<Long> savedReportIds);
}
