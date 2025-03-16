package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.domain.testEnv.BikeOwner;
import be.kdg.swiftby.presentation.webapi.dto.BikeOwnerApiMapper;
import be.kdg.swiftby.presentation.webapi.dto.response.BikeOwnerApiResponseDto;
import be.kdg.swiftby.presentation.webapi.dto.response.BikeReportApiResponseDto;
import be.kdg.swiftby.service.intf.BikeOwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bikeowners")
public class BikeOwnerApiController {
    BikeOwnerService bikeOwnerService;
    BikeOwnerApiMapper bikeOwnerApiMapper;

    public BikeOwnerApiController(BikeOwnerService bikeOwnerService, BikeOwnerApiMapper bikeOwnerApiMapper) {
        this.bikeOwnerService = bikeOwnerService;
        this.bikeOwnerApiMapper = bikeOwnerApiMapper;
    }

    @GetMapping("")
    public ResponseEntity<List<BikeOwnerApiResponseDto>> getAll() {
        return ResponseEntity.ok(bikeOwnerApiMapper.toBikeOwnerDtoList(bikeOwnerService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BikeOwnerApiResponseDto> getBikeOwnerById(@PathVariable Long id) {
        return ResponseEntity.ok(bikeOwnerApiMapper.toBikeOwnerDto(bikeOwnerService.getById(id)));
    }
}
