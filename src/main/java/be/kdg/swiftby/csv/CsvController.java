package be.kdg.swiftby.csv;

import be.kdg.swiftby.domain.bike.Bike;
import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.domain.testEnv.BikeOwner;
import be.kdg.swiftby.presentation.webapi.dto.request.StartTestRequestDto;
import be.kdg.swiftby.service.dto.BikeDto;
import be.kdg.swiftby.service.dto.MotorDto;
import be.kdg.swiftby.service.intf.BikeOwnerService;
import be.kdg.swiftby.service.intf.BikeReportService;
import be.kdg.swiftby.service.intf.BikeService;
import be.kdg.swiftby.websocket.TestWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/csv")
@RequiredArgsConstructor
public class CsvController {
    private final CsvService csvService;
    private final BikeReportService bikeReportService;
    private final BikeOwnerService bikeOwnerService;
    private final BikeService bikeService;
    private final TestWebSocketHandler testWebSocketHandler;

    /**
     * 🔹 Process CSV file and store reports.
     */
    @PostMapping("/process-latest")
    public ResponseEntity<String> processLatestCsv() {
        try {
            List<BikeReportCsvRecord> bikeReportRecords = csvService.processLatestCsvFile();

            if (bikeReportRecords.isEmpty()) {
                return ResponseEntity.badRequest().body("⚠️ No records found in CSV file.");
            }

            // Track saved report IDs
            List<Long> savedReportIds = bikeReportRecords.stream()
                    .map(record -> bikeReportService.save(
                            1L,
                            record.reportTime(),
                            record.mileage(),
                            record.assistanceLevel(),
                            record.technicianComment(),
                            record.axialSensorDataDto(),
                            record.batteryDataDto(),
                            record.motorDataDto(),
                            record.pedalDataDto(),
                            record.testBenchDataDto(),
                            record.wheelDataDto()
                    ).getId())
                    .toList();

            BikeReportSummary summary = bikeReportService.saveReportSummaryFromSavedReports(savedReportIds);
            return ResponseEntity.ok("✅ CSV successfully processed and summary saved: " + summary);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("🚨 Error processing CSV: " + e.getMessage());
        }
    }

//    @PostMapping("/start-test")
//    public ResponseEntity<?> startTestFromCsv(@RequestBody StartTestRequestDto testRequest) {
//        try {
//            // 1️⃣ **Save Bike Owner**
//            BikeOwner ownerId = bikeOwnerService.save(
//                    testRequest.getOwnerEmail(),
//                    "FAKE PASSWORD",
//                    testRequest.getOwnerFirstName(),
//                    testRequest.getOwnerLastName(),
//                    testRequest.getOwnerPhoneNumber()
//            );
//
//            // 2️⃣ **Create & Save Motor**
//            MotorDto motorDto = new MotorDto(
//                    testRequest.getEngineType(),
//                    testRequest.getGearType(),
//                    testRequest.getMaxPower(),
//                    testRequest.getNominalPower(),
//                    testRequest.getTorque()
//            );
//
//            // 3️⃣ **Create & Save Bike**
//            BikeDto bikeDto = new BikeDto(
//                    null, // Auto-generated ID
//                    testRequest.getBrand(),
//                    testRequest.getType(),
//                    testRequest.getChassisNumber(),
//                    testRequest.getPowertrain(),
//                    testRequest.getBikeSize(),
//                    testRequest.getMaxSupport(),
//                    motorDto,
//                    testRequest.getBatteryCapacity()
//            );
//
//            Bike bike = bikeService.save(bikeDto);
//
//
//            UUID testId = UUID.randomUUID(); // Generate a test ID
//            testWebSocketHandler.trackTest(testId, bike.getId());
//
//            return ResponseEntity.ok("✅ Test started successfully! Test ID: " + testId);
//
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("🚨 Error starting test: " + e.getMessage());
//        }
//    }
}
