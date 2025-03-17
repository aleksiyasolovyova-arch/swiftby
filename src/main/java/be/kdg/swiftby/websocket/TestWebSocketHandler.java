package be.kdg.swiftby.websocket;

import be.kdg.swiftby.csv.CsvService;
import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.service.TestState;
import be.kdg.swiftby.service.dto.api.dto.TestDto;
import be.kdg.swiftby.service.intf.BikeReportService;
import be.kdg.swiftby.service.intf.TestBenchApiService;
import lombok.RequiredArgsConstructor;
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
        System.out.println("Tracking test: " + testId + " for Bike ID: " + bikeId);
        ongoingTests.put(testId, bikeId);
        sendUpdate(testId, TestState.STARTED);
    }

    @Scheduled(fixedRate = 3000)
    public void checkTestStatus() {
        System.out.println("🔍 Checking test statuses...");

        for (UUID testId : ongoingTests.keySet()) {
            System.out.println("Checking test ID: " + testId);
            TestDto testDto = testService.getTest(testId);
            TestState testState = testDto.state();
            System.out.println("Test " + testId + " is in state: " + testState);
            sendUpdate(testId, testState);
            if (testState == TestState.COMPLETED) {
                System.out.println("Test " + testId + " completed. Fetching report & processing CSV...");
                testService.getReport(testId);
                processCsvAfterTestCompletion(ongoingTests.get(testId));
                ongoingTests.remove(testId);
            }
        }
    }
    private void processCsvAfterTestCompletion(Long bikeId) {
        try {
            List<Long> savedReportIds = csvService.processLatestCsvFile().stream()
                    .map(record -> bikeReportService.save(
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
                    ).getId())
                    .toList();

            BikeReportSummary summary = bikeReportService.saveReportSummaryFromSavedReports(savedReportIds);
            System.out.println(" CSV processed and summary saved: " + summary);
        } catch (Exception e) {
            System.out.println("Error in the csv" + e);
        }
    }
    private void sendUpdate(UUID testId, TestState status) {
        String message = "{\"testId\": \"" + testId + "\", \"status\": \"" + status + "\"}";
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
