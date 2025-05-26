package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.report.BikeReport;
import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.service.dto.BearingHealthEvaluation;
import be.kdg.swiftby.service.dto.BikeReportChartDto;
import be.kdg.swiftby.service.dto.ReportChartSeriesDto;
import be.kdg.swiftby.service.dto.ServiceSummaryIdDateDto;
import be.kdg.swiftby.service.dto.data.BatteryTestDto;
import be.kdg.swiftby.service.dto.data.NominalLoadTestDto;
import be.kdg.swiftby.service.dto.data.TestProcedureOverviewDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BikeReportSummaryService {
    List<BikeReportSummary> getAllSummaries();
    BikeReportSummary getSummaryById(Long id);
    List<BikeReportSummary> getSummariesByBikeId(Long bikeId);
//    BikeReportSummary getSummaryByBikeAndDate(Long bikeId, LocalDate reportDate);


    BikeReportSummary getSummaryByBikeAndDate(Long bikeId, LocalDate reportDate);


    List<BikeReportChartDto> getChartDataWithInterval(Long summaryId, String mode, int intervalSeconds);

    TestProcedureOverviewDto getTestProcedureOverview(Long summaryId);

    BikeReportSummary getSummaryWithCheck(Long id);

    NominalLoadTestDto getNominalLoadTest(Long summaryId);

    BatteryTestDto getBatteryTest(Long summaryId);



    BearingHealthEvaluation evaluateBearingHealth(Long summaryId);

    void attachFunctionalityCheck(Long summaryId, Long checkId);


    List<BikeReportSummary> getSummariesByBikeInstanceId(Long bikeInstanceId);

    List<ReportChartSeriesDto> getFieldOverTimeForTwoReports(Long summary1Id, Long summary2Id, String field, int intervalSeconds);

    ReportChartSeriesDto buildFieldSeriesFromReportsWithInterval(BikeReportSummary summary, String field, int intervalSeconds);

    ReportChartSeriesDto buildFieldSeriesFromReports(BikeReportSummary summary, String field);


    List<ReportChartSeriesDto> compareSummaryFields(Long summary1Id, Long summary2Id);

    List<ServiceSummaryIdDateDto> getAvailableComparisons(Long summaryId);

    List<BikeReportSummary> getAllSummariesByBikeOwnerId(Long bikeOwnerId);
}
