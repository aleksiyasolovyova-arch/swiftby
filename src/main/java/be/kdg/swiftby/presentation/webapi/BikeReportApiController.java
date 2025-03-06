package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.presentation.webapi.dto.bikereport.BikeReportMapperApi;
import be.kdg.swiftby.presentation.webapi.dto.request.BikeReportRequestDto;
import be.kdg.swiftby.presentation.webapi.dto.response.BikeReportDto;
import be.kdg.swiftby.service.dto.*;
import be.kdg.swiftby.service.intf.BikeReportService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bikereports")
public class BikeReportApiController {
    private final BikeReportService bikeReportService;
    private final BikeReportMapperApi bikeReportMapper;
    Logger log = LoggerFactory.getLogger(BikeReportApiController.class);

    @GetMapping
    public ResponseEntity<List<BikeReportDto>> getAll(){
        System.out.println("meow");
        System.out.println(bikeReportService.getAllWithBikes());
        System.out.println("purr");
        List<BikeReportDto> bikeReportDtos=bikeReportService.getAllWithBikes().stream()
                .map(bikeReportMapper::toBikeReportDto)
                .toList();
        return ResponseEntity.ok(bikeReportDtos);
    }
    @PostMapping
    public ResponseEntity<BikeReportDto> createReport(@RequestBody BikeReportRequestDto requestDto) {

        var savedReport = bikeReportService.save(
                requestDto.bikeId(),
                requestDto.reportTime(),
                requestDto.mileage(),
                requestDto.assistanceLevel(),
                requestDto.technicianComment(),
                new AxialSensorDataDto(requestDto.axialSensorData().horizontalInclination(),
                        requestDto.axialSensorData().horizontalInclination()),
                new BatteryDataDto(requestDto.batteryData().chargeStatus(),
                        requestDto.batteryData().current(),
                        requestDto.batteryData().voltage(),
                        requestDto.batteryData().capacity(),
                        requestDto.batteryData().temperature()),
                new MotorDataDto(requestDto.motorData().engine(),
                        requestDto.motorData().enginePower()),
                new PedalDataDto(requestDto.pedalData().torqueCrank(),
                        requestDto.pedalData().cadence()),
                new TestBenchDataDto(requestDto.testBenchData().rollerTorque(),
                        requestDto.testBenchData().loadCell(),
                        requestDto.testBenchData().rol(),
                        requestDto.testBenchData().loadPower(),
                        requestDto.testBenchData().statusPlug(),
                        requestDto.testBenchData().testBenchId()),
                new WheelDataDto(requestDto.wheelData().speed(),
                        requestDto.wheelData().power())
        );

        var responseDto = bikeReportMapper.toBikeReportDto(savedReport);
        return ResponseEntity.ok(responseDto);
    }


}
