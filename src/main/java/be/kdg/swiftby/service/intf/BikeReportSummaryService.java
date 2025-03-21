package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.report.BikeReportSummary;

import java.time.LocalDate;
import java.util.List;

public interface BikeReportSummaryService {
    List<BikeReportSummary> getAllSummaries();
    BikeReportSummary getSummaryById(Long id);
    List<BikeReportSummary> getSummariesByBikeId(Long bikeId);
    BikeReportSummary getSummaryByBikeAndDate(Long bikeId, LocalDate reportDate);
}
