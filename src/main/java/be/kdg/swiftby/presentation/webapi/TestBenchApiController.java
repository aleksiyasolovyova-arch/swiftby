package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.domain.bike.BikeInstance;
import be.kdg.swiftby.presentation.webapi.dto.request.StartTestRequestDto;
import be.kdg.swiftby.presentation.webapi.dto.response.TestResponseDto;
import be.kdg.swiftby.service.TestType;
import be.kdg.swiftby.service.dto.api.dto.TestDto;
import be.kdg.swiftby.service.intf.BikeInstanceService;
import be.kdg.swiftby.service.intf.TestBenchApiService;
import be.kdg.swiftby.service.intf.TestBenchService;
import be.kdg.swiftby.websocket.TestWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestBenchApiController {

    private final TestBenchApiService testService;
    private final TestBenchService testBenchService;
    private final BikeInstanceService bikeInstanceService;
    private final TestWebSocketHandler testWebSocketHandler;

    @PostMapping("/start")
    public ResponseEntity<TestResponseDto> startTest(@RequestBody StartTestRequestDto request) {
        BikeInstance bikeInstance = bikeInstanceService.getByIdWithModelAndMotor(request.getBikeId());

        int batteryCapacity = bikeInstance.getModel().getBatteryCapacity();
        int maxSupport = bikeInstance.getModel().getMaxSupport();
        int enginePowerMax = bikeInstance.getModel().getMotor().getMaxPower();
        int enginePowerNominal = bikeInstance.getModel().getMotor().getNominalPower();
        int engineTorque = bikeInstance.getModel().getMotor().getTorque();

        TestDto testDto = testService.startTest(
                TestType.valueOf(request.getTestType().name()),
                batteryCapacity,
                maxSupport,
                enginePowerMax,
                enginePowerNominal,
                engineTorque,
                bikeInstance.getId()
        );

        testWebSocketHandler.trackTest(testDto.id(), bikeInstance.getId());
        return ResponseEntity.ok(mapToResponseDto(testDto));
    }

    @GetMapping("/{testId}")
    public ResponseEntity<TestResponseDto> getTest(@PathVariable UUID testId) {
        TestDto test = testService.getTest(testId);
        return ResponseEntity.ok(mapToResponseDto(test));
    }

    @GetMapping("/{testId}/report")
    public ResponseEntity<Void> downloadReport(@PathVariable UUID testId) {
        testService.getReport(testId);
        return ResponseEntity.ok().build();
    }

    private TestResponseDto mapToResponseDto(TestDto test) {
        return new TestResponseDto(
                test.id(),
                test.expiryDate(),
                test.state(),
                test.type(),
                test.batteryCapacity(),
                test.maxSupport(),
                test.enginePowerMax(),
                test.enginePowerNominal(),
                test.engineTorque()
        );
    }
}
