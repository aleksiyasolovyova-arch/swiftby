package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.domain.bike.Bike;
import be.kdg.swiftby.domain.testEnv.BikeOwner;
import be.kdg.swiftby.presentation.webapi.dto.request.StartTestRequestDto;
import be.kdg.swiftby.presentation.webapi.dto.request.TestRequestDto;
import be.kdg.swiftby.presentation.webapi.dto.response.TestResponseDto;
import be.kdg.swiftby.service.TestType;
import be.kdg.swiftby.service.dto.BikeDto;
import be.kdg.swiftby.service.dto.MotorDto;
import be.kdg.swiftby.service.dto.api.dto.TestDto;
import be.kdg.swiftby.service.intf.BikeOwnerService;
import be.kdg.swiftby.service.intf.BikeService;
import be.kdg.swiftby.service.intf.TestBenchApiService;
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
    private final BikeOwnerService bikeOwnerService;
    private final BikeService bikeService;
    private final TestWebSocketHandler testWebSocketHandler;

    @PostMapping("/start")
    public ResponseEntity<TestResponseDto> startTest(@RequestBody StartTestRequestDto request) {
        // save the bike owner
        BikeOwner bikeOwner = bikeOwnerService.save(
                request.getOwnerEmail(),
                // the technician can't input the password for the owner
                // TODO: add a new method without a password
                "DUMMY DUM PASSWORD",
                request.getOwnerFirstName(),
                request.getOwnerLastName(),
                request.getOwnerPhoneNumber()
        );
        // save the bike info
        Bike bike = bikeService.save(
                new BikeDto(
                        request.getBrand(),
                        request.getBrand(),
                        request.getChassisNumber(),
                        request.getPowertrain(),
                        request.getBikeSize(),
                        request.getMaxSupport(),
                        new MotorDto(
                                request.getEngineType(),
                                request.getGearType(),
                                request.getMaxPower(),
                                request.getNominalPower(),
                                request.getTorque()
                        ),
                        request.getBatteryCapacity()
                )
        );

        TestDto testDto = testService.startTest(
                TestType.valueOf(request.getTestType().toUpperCase()),
                request.getBatteryCapacity(),
                request.getMaxSupport(),
                request.getMaxPower(),
                request.getNominalPower(),
                request.getTorque(),
                bike.getId()
        );

        testWebSocketHandler.trackTest(testDto.id(),bike.getId());

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
