package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.service.TestType;
import be.kdg.swiftby.service.dto.api.dto.StartTestDto;
import be.kdg.swiftby.service.dto.api.dto.TestDto;
import be.kdg.swiftby.service.intf.TestBenchApiService;
import be.kdg.swiftby.websocket.TestWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TestBenchApiServiceImpl implements TestBenchApiService {
    private final RestTemplate restTemplate;
    private final TestWebSocketHandler testWebSocketHandler;

    @Value("${testbenchapi.link}")
    private String baseUrl;

    @Value("${testbenchapi.key}")
    private String apiKey;

    @Override
    public TestDto startTest(
            TestType testType,
            int batteryCapacity,
            int maxSupport,
            int enginePowerMax,
            int enginePowerNominal,
            int engineTorque,
            Long bikeId
    ) {
        String url = baseUrl ;
        StartTestDto requestBody = new StartTestDto(testType, batteryCapacity, maxSupport, enginePowerMax, enginePowerNominal, engineTorque);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<StartTestDto> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            System.out.println("🚀 Sending test request to API...");

            ResponseEntity<TestDto> response = restTemplate.postForEntity(url, requestEntity, TestDto.class);

            if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
                TestDto testDto = response.getBody();
                if (testDto != null) {
                    System.out.println("Test started successfully. ID: " + testDto.id());
                    testWebSocketHandler.trackTest(testDto.id(),bikeId);

                    return testDto;
                }
            }
            throw new RuntimeException("API returned empty response.");
        } catch (Exception e) {
            throw new RuntimeException("unexpected error occurred: " + e.getMessage());
        }
    }
    @Override
    public TestDto getTest(
            UUID testId
    ) {
        String url = baseUrl +"/" + testId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        try {
            ResponseEntity<TestDto> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, TestDto.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.err.println("error fetching test details: " + e.getStatusCode());
            return null;
        }
    }
    @Override
    public void getReport(UUID testId) {
        String url = baseUrl + "/" + testId + "/report";
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        try {
            ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, byte[].class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                saveReportToFile(response.getBody(), testId);
            } else {
                System.err.println("report request failed. Response: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            System.err.println("error fetching report: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        }
    }
    private void saveReportToFile(byte[] reportData, UUID testId) {
        String directoryPath = "reports";
        String filePath = directoryPath + "/report_" + testId + ".csv";

        try {
            Files.createDirectories(Paths.get(directoryPath));

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(reportData);
            }
            System.out.println("report saved successfully: " + filePath);
        } catch (IOException e) {
            System.err.println("error saving report: " + e.getMessage());
        }
    }
}
