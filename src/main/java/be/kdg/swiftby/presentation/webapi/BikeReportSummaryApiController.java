package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.presentation.webapi.dto.BearingHealthResultDto;
import be.kdg.swiftby.presentation.webapi.dto.BikeReportSummaryApiMapper;
import be.kdg.swiftby.presentation.webapi.dto.response.BikeReportSummaryDto;
import be.kdg.swiftby.repository.report.BikeReportSummaryRepository;
import be.kdg.swiftby.service.dto.BearingHealthEvaluation;
import be.kdg.swiftby.service.dto.BikeReportChartDto;
import be.kdg.swiftby.service.dto.ReportChartSeriesDto;
import be.kdg.swiftby.presentation.webapi.dto.SummaryIdDateDto;
import be.kdg.swiftby.service.dto.data.BatteryTestDto;
import be.kdg.swiftby.service.dto.data.NominalLoadTestDto;
import be.kdg.swiftby.service.dto.data.TestProcedureOverviewDto;
import be.kdg.swiftby.service.intf.BikeReportService;
import be.kdg.swiftby.service.intf.BikeReportSummaryPdfService;
import be.kdg.swiftby.service.intf.BikeReportSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/report-summaries")
@RequiredArgsConstructor
public class BikeReportSummaryApiController {
    private final BikeReportSummaryService bikeReportSummaryService;
    private final BikeReportSummaryApiMapper bikeReportSummaryApiMapper;
    private final BikeReportSummaryPdfService bikeReportSummaryPdfService;
    private final BikeReportService bikeReportService;
    private final BikeReportSummaryRepository bikeReportSummaryRepository;

    @GetMapping("/{id}")
    public ResponseEntity<BikeReportSummaryDto> getSummaryById(@PathVariable Long id) {
        return ResponseEntity.ok(bikeReportSummaryApiMapper.toBikeReportSummaryDto(bikeReportSummaryService.getSummaryById(id)));
    }


    @PatchMapping("/{summaryId}/attach-check/{checkId}")
    public ResponseEntity<Void> attachCheck(@PathVariable Long summaryId, @PathVariable Long checkId) {
        bikeReportService.attachFunctionalityCheck(summaryId, checkId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BikeReportSummaryDto>> getAll(){
        return ResponseEntity.ok(bikeReportSummaryService.getAllSummaries().stream()
                .map(bikeReportSummaryApiMapper::toBikeReportSummaryDto)
                .toList());
    }



    @GetMapping("/{bikeId}/generate-pdf")
    public ResponseEntity<byte[]> generateReportPdfByBikeAndDate(@PathVariable Long bikeId,
                                                    @RequestParam("reportDate") String reportDate) {
        // Convert to LocalDateTime to match database format
        LocalDateTime startOfDay = LocalDate.parse(reportDate).atStartOfDay();

        BikeReportSummary summary = bikeReportSummaryService.getSummaryByBikeAndDate(bikeId, startOfDay.toLocalDate());

        if (summary == null) {
            return ResponseEntity.badRequest().body(null);
        }

        byte[] pdfBytes = bikeReportSummaryPdfService.generatePdf(summary);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=BikeReport_" + bikeId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
    @GetMapping("/{id}/generatePdf")
    public ResponseEntity<byte[]> generateReportPdf(@PathVariable Long id) {

        BikeReportSummary summary = bikeReportSummaryService.getSummaryById(id);

        if (summary == null) {
            return ResponseEntity.badRequest().body(null);
        }

        byte[] pdfBytes = bikeReportSummaryPdfService.generatePdf(summary);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=BikeReport_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);

    }




    @GetMapping("/{summaryId}/nominal-load")
    public ResponseEntity<NominalLoadTestDto> getNominalLoad(@PathVariable Long summaryId) {
        return ResponseEntity.ok(bikeReportSummaryService.getNominalLoadTest(summaryId));
    }


    @GetMapping("/{summaryId}/battery-test")
    public ResponseEntity<BatteryTestDto> getBatteryTest(@PathVariable Long summaryId) {
        return bikeReportSummaryRepository.getBatteryTestData(summaryId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/{summaryId}/bearing-health")
    public ResponseEntity<BearingHealthResultDto> evaluateBearingHealth(
            @PathVariable Long summaryId
    ) {
        BearingHealthEvaluation evaluation = bikeReportSummaryService.evaluateBearingHealth(summaryId);

        BikeReportSummary summary = bikeReportSummaryRepository.findById(summaryId)
                .orElseThrow(() -> new RuntimeException("Summary not found"));

        BearingHealthResultDto dto = new BearingHealthResultDto(
                evaluation.horizontalRange(),
                evaluation.verticalRange(),
                summary.getHorizontalInclination(),
                summary.getVerticalInclination(),
                evaluation.isBad() ? "Bad" : "Good"
        );

        return ResponseEntity.ok(dto);
    }








    @GetMapping("/{summaryId}/test-procedure-overview")
    public ResponseEntity<TestProcedureOverviewDto> getTestProcedureOverview(@PathVariable Long summaryId) {
        return ResponseEntity.ok(bikeReportSummaryService.getTestProcedureOverview(summaryId));
    }


    @GetMapping("/{summaryId}/chart-data")
    public List<BikeReportChartDto> getChartData(
            @PathVariable Long summaryId,
            @RequestParam(defaultValue = "raw") String mode,
            @RequestParam(defaultValue = "1") int intervalSeconds
    ) {
        return bikeReportSummaryService.getChartDataWithInterval(summaryId, mode, intervalSeconds);
    }

    @GetMapping("/compare-field-over-time")
    public ResponseEntity<List<ReportChartSeriesDto>> compareFieldOverTime(
            @RequestParam Long summary1Id,
            @RequestParam Long summary2Id,
            @RequestParam String field,
            @RequestParam(defaultValue = "1") int intervalSeconds
    ) {
        var result = bikeReportSummaryService.getFieldOverTimeForTwoReports(summary1Id, summary2Id, field, intervalSeconds);
        return ResponseEntity.ok(result);
    }




    @GetMapping("/compare-summary-values")
    public ResponseEntity<List<ReportChartSeriesDto>> compareSummaryValues(
            @RequestParam Long summary1,
            @RequestParam Long summary2
    ) {
        return ResponseEntity.ok(bikeReportSummaryService.compareSummaryFields(summary1, summary2));
    }

    @GetMapping("/bike/{bikeId}")
    public List<SummaryIdDateDto> getSummariesForBike(@PathVariable Long bikeId) {
        return bikeReportSummaryService.getSummariesByBikeId(bikeId).stream()
                .map(s -> new SummaryIdDateDto(
                        s.getId(),
                        s.getReportTime() != null ? s.getReportTime().toString() : "Unknown"
                ))
                .toList();
    }

    @GetMapping("/reports-available")
    public ResponseEntity<List<SummaryIdDateDto>> getReportsAvailable(@RequestParam Long summaryId) {
       var availableReports = bikeReportSummaryService.getAvailableComparisons(summaryId).stream()
               .map(s -> new SummaryIdDateDto  (
                       s.id(),
                       s.date()

               )).toList();
       if(availableReports.isEmpty()) {
           return ResponseEntity.noContent().build();
       }
        return ResponseEntity.ok( availableReports);
    }
}
