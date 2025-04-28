package be.kdg.swiftby.websocket;

import be.kdg.swiftby.csv.CsvService;
import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.email.EmailService;
import be.kdg.swiftby.service.TestState;
import be.kdg.swiftby.service.dto.api.dto.StartTestDto;
import be.kdg.swiftby.service.dto.api.dto.TestDto;
import be.kdg.swiftby.service.intf.BikeReportService;
import be.kdg.swiftby.service.intf.BikeReportSummaryPdfService;
import be.kdg.swiftby.service.intf.TestBenchApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
//@RequiredArgsConstructor
public class TestWebSocketHandler extends TextWebSocketHandler {
    private final CsvService csvService;
    private final BikeReportService bikeReportService;
    private final ConcurrentHashMap<UUID, StartTestDto> testMetadata = new ConcurrentHashMap<>();

    private final TestBenchApiService testService;
    private final EmailService emailService;
    private final BikeReportSummaryPdfService bikeReportSummaryPdfService;

    public TestWebSocketHandler(
            BikeReportService bikeReportService,
            CsvService csvService,
            @Lazy
            TestBenchApiService testService,
            EmailService emailService, BikeReportSummaryPdfService bikeReportSummaryPdfService
    ) {
        this.bikeReportService = bikeReportService;
        this.csvService = csvService;
        this.testService = testService;
        this.emailService = emailService;
        this.bikeReportSummaryPdfService = bikeReportSummaryPdfService;
    }

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ConcurrentHashMap<UUID, Long> ongoingTests = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    public void trackTest(UUID testId, Long bikeId) {
        System.out.println("Tracking test: " + testId + " for Bike ID: " + bikeId);
        ongoingTests.put(testId, bikeId);
        sendUpdate(testId, TestState.STARTED, null);
    }

    @Scheduled(fixedRate = 3000)
    public void checkTestStatus() throws IOException {
        System.out.println("🔄 Checking test statuses...");

        for (UUID testId : ongoingTests.keySet()) {
            System.out.println("📡 Checking test ID: " + testId);
            TestDto testDto = testService.getTest(testId);
            TestState testState = testDto.state();
            System.out.println("✅ Test " + testId + " is in state: " + testState);

            if (testState == TestState.COMPLETED) {
                System.out.println("🚀 Test " + testId + " completed. Fetching report & processing CSV...");

                testService.getReport(testId);
                StartTestDto startData = testMetadata.remove(testId);
                BikeReportSummary summary = processCsvAfterTestCompletion(ongoingTests.get(testId), startData);



                if (summary != null) {
                    System.out.println("✅ Summary generated with ID: " + summary.getId());
                    byte[] pdfBytes = bikeReportSummaryPdfService.generatePdf(summary);

                    Path uploadPath = Paths.get("src/main/resources/uploads/");
                    Files.createDirectories(uploadPath);

                    Path filePath = uploadPath.resolve(summary.getId() + ".pdf");
                    Files.write(filePath, pdfBytes);
                    System.out.println("✅ PDF generated and saved at: " + filePath);

                    try {
                        String to = "daniil.mumladze@student.kdg.be";
                        String subject = "Test Completed - Bike Report";
                        String body = "Your bike test is finished. Summary attached!";
                        String attachmentPath = filePath.toString();

                        emailService.sendEmail(to, subject, body, attachmentPath);
                        System.out.println("✅ Email sent successfully!");
                    } catch (Exception e) {
                        System.err.println("❌ Failed to send email: " + e.getMessage());
                        e.printStackTrace();
                    }

                } else {
                    System.out.println("❌ Summary generation failed!");
                }

                Long summaryId = (summary != null) ? summary.getId() : null;
                ongoingTests.remove(testId);

                sendUpdate(testId, TestState.COMPLETED, summaryId);
            }
        }
    }

    private BikeReportSummary processCsvAfterTestCompletion(Long bikeId, StartTestDto startData) {
        try {
            System.out.println("🚴 Processing CSV for Bike ID: " + bikeId);

            List<Long> savedReportIds = csvService.processLatestCsvFile().stream()
                    .map(record -> {
                        Long reportId = bikeReportService.save(
                                bikeId,
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
                        ).getId();
                        System.out.println("✅ Saved Report ID: " + reportId);
                        return reportId;
                    })
                    .toList();

            System.out.println("🔄 Generating Summary Report from Reports: " + savedReportIds);
            BikeReportSummary summary = bikeReportService.saveReportSummaryFromSavedReports(savedReportIds);

            if (summary != null) {
                System.out.println("✅ Summary Created Successfully: " + summary.getId());
            } else {
                System.out.println("❌ Summary creation failed!");
            }

            return summary;
        } catch (Exception e) {
            System.err.println("❌ Error processing CSV: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private void sendUpdate(UUID testId, TestState status, Long summaryId) {
        System.out.println("📡 Sending WebSocket Update:");
        System.out.println("🆔 Test ID: " + testId);
        System.out.println("📄 Summary ID: " + summaryId);
        System.out.println("🔄 Status: " + status);

        String message = "{\"testId\": \"" + testId + "\", \"status\": \"" + status + "\", \"summaryId\": " + (summaryId != null ? summaryId : "null") + "}";

        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message));
                System.out.println("✅ WebSocket message sent successfully!");
            } catch (IOException e) {
                System.err.println("❌ WebSocket Error sending message: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void storeStartTestData(UUID testId, StartTestDto dto) {
        testMetadata.put(testId, dto);
    }


}
