package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.domain.report.BikeReport;
import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.presentation.webapi.dto.BikeReportSummaryApiMapper;
import be.kdg.swiftby.presentation.webapi.dto.response.BikeReportApiResponseDto;
import be.kdg.swiftby.presentation.webapi.dto.response.BikeReportSummaryDto;
import be.kdg.swiftby.service.dto.BikeReportChartDto;
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
//
//    @PostMapping("/api/report-summaries/{summaryId}/attach-functional-check/{checkId}")
//    public ResponseEntity<Void> attachCheck(
//            @PathVariable Long summaryId,
//            @PathVariable Long checkId
//    ) {
//        bikeReportService.attachFunctionalityCheck(summaryId, checkId);
//        return ResponseEntity.ok().build();
//    }


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

    @GetMapping("/{summaryId}/chart-data")
    public ResponseEntity<List<BikeReportChartDto>> getChartData(@PathVariable Long summaryId) {
        return ResponseEntity.ok(bikeReportSummaryService.getChartDataForSummary(summaryId));
    }







}
