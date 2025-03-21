package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.presentation.webapi.dto.bikereport.BikeReportMapperApi;
import be.kdg.swiftby.presentation.webapi.dto.request.BikeReportRequestDto;
import be.kdg.swiftby.presentation.webapi.dto.response.BikeReportApiResponseDto;
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
    public ResponseEntity<List<BikeReportApiResponseDto>> getAll(){
        System.out.println("meow");
        System.out.println(bikeReportService.getAllWithBikes());
        System.out.println("purr");
        List<BikeReportApiResponseDto> bikeReportDtos=bikeReportService.getAllWithBikes().stream()
                .map(bikeReportMapper::toBikeReportDto)
                .toList();
        return ResponseEntity.ok(bikeReportDtos);
    }

}
