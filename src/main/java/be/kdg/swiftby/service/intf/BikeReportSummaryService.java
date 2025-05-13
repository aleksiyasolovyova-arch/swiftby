package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.service.dto.BikeReportChartDto;
import be.kdg.swiftby.service.dto.data.BatteryTestDto;
import be.kdg.swiftby.service.dto.data.NominalLoadTestDto;
import be.kdg.swiftby.service.dto.data.TestProcedureOverviewDto;

import java.time.LocalDate;
import java.util.List;

public interface BikeReportSummaryService {
    List<BikeReportSummary> getAllSummaries();

    BikeReportSummary getSummaryById(Long id);

    List<BikeReportSummary> getSummariesByBikeId(Long bikeId);
//    BikeReportSummary getSummaryByBikeAndDate(Long bikeId, LocalDate reportDate);


    BikeReportSummary getSummaryByBikeAndDate(Long bikeId, LocalDate reportDate);

//    List<BikeReportChartDto> getChartDataForSummary(Long summaryId);


    List<BikeReportChartDto> getChartDataWithInterval(Long summaryId, String mode, int intervalSeconds);

    TestProcedureOverviewDto getTestProcedureOverview(Long summaryId);

    BikeReportSummary getSummaryWithCheck(Long id);

    NominalLoadTestDto getNominalLoadTest(Long summaryId);

    BatteryTestDto getBatteryTest(Long summaryId);

    String evaluateAndStoreBearingHealth(Long summaryId, double horizontalThreshold, double verticalThreshold);

    void attachFunctionalityCheck(Long summaryId, Long checkId);
}
