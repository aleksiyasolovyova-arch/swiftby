package be.kdg.swiftby.websocket;

import be.kdg.swiftby.csv.CsvService;
import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.service.TestState;
import be.kdg.swiftby.service.dto.api.dto.StartTestDto;
import be.kdg.swiftby.service.dto.api.dto.TestDto;
import be.kdg.swiftby.service.intf.BikeReportService;
import be.kdg.swiftby.service.intf.TestBenchApiService;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
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

    public TestWebSocketHandler(
            BikeReportService bikeReportService,
            CsvService csvService,
            @Lazy
            TestBenchApiService testService
    ) {
        this.bikeReportService = bikeReportService;
        this.csvService = csvService;
        this.testService = testService;
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
        ongoingTests.put(testId, bikeId);
        sendUpdate(testId, TestState.STARTED, null);
    }

    @Scheduled(fixedRate = 3000)
    public void checkTestStatus() {
        for (UUID testId : ongoingTests.keySet()) {
            TestDto testDto = testService.getTest(testId);
            TestState testState = testDto.state();

            if (testState == TestState.COMPLETED) {

                testService.getReport(testId);
                StartTestDto startData = testMetadata.remove(testId);
                BikeReportSummary summary = processCsvAfterTestCompletion(ongoingTests.get(testId), startData);
                Long summaryId = (summary != null) ? summary.getId() : null;
                ongoingTests.remove(testId);

                sendUpdate(testId, TestState.COMPLETED, summaryId);
            }
        }
    }

    private BikeReportSummary processCsvAfterTestCompletion(Long bikeId, StartTestDto startData) {
        try {

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
                        return reportId;
                    })
                    .toList();

            BikeReportSummary summary = bikeReportService.saveReportSummaryFromSavedReports(savedReportIds);


            return summary;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sendUpdate(UUID testId, TestState status, Long summaryId) {

        String message = "{\"testId\": \"" + testId + "\", \"status\": \"" + status + "\", \"summaryId\": " + (summaryId != null ? summaryId : "null") + "}";

        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                System.out.println("websocket error sending message: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void storeStartTestData(UUID testId, StartTestDto dto) {
        testMetadata.put(testId, dto);
    }


}
