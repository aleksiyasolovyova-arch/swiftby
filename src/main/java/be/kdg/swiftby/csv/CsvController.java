package be.kdg.swiftby.csv;

import be.kdg.swiftby.service.intf.BikeOwnerService;
import be.kdg.swiftby.service.intf.BikeReportService;
import be.kdg.swiftby.service.intf.BikeService;
import be.kdg.swiftby.websocket.TestWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/csv")
@RequiredArgsConstructor
public class CsvController {
    private final CsvService csvService;
    private final BikeReportService bikeReportService;
    private final BikeOwnerService bikeOwnerService;
    private final BikeService bikeService;
    private final TestWebSocketHandler testWebSocketHandler;


//    @PostMapping("/process-latest")
//    public ResponseEntity<String> processLatestCsv() {
//        try {
//            List<BikeReportCsvRecord> bikeReportRecords = csvService.processLatestCsvFile();
//
//            if (bikeReportRecords.isEmpty()) {
//                return ResponseEntity.badRequest().body("No records found in CSV file.");
//            }
//
//            // Track saved report IDs
//            List<Long> savedReportIds = bikeReportRecords.stream()
//                    .map(record -> bikeReportService.save(
//                            1L,
//                            record.reportTime(),
//                            record.mileage(),
//                            record.assistanceLevel(),
//                            record.technicianComment(),
//                            record.axialSensorDataDto(),
//                            record.batteryDataDto(),
//                            record.motorDataDto(),
//                            record.pedalDataDto(),
//                            record.testBenchDataDto(),
//                            record.wheelDataDto()
//                    ).getId())
//                    .toList();
//
//            BikeReportSummary summary = bikeReportService.saveReportSummaryFromSavedReports(savedReportIds);
//            return ResponseEntity.ok("CSV successfully processed and summary saved: " + summary);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(" Error processing CSV: " + e.getMessage());
//        }
//    }
//
}
