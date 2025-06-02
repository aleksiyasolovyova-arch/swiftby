package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.domain.bike.BikeInstance;
import be.kdg.swiftby.domain.testEnv.BikeOwner;
import be.kdg.swiftby.domain.testEnv.PasswordResetToken;
import be.kdg.swiftby.presentation.webapi.dto.BikeOwnerApiMapper;
import be.kdg.swiftby.presentation.webapi.dto.bikereport.BikeMapperApi;
import be.kdg.swiftby.presentation.webapi.dto.request.BikeOwnerRequestDto;
import be.kdg.swiftby.presentation.webapi.dto.response.BikeApiResponseDto;
import be.kdg.swiftby.presentation.webapi.dto.response.BikeOwnerApiResponseDto;
import be.kdg.swiftby.presentation.webapi.dto.response.BikeReportApiResponseDto;
import be.kdg.swiftby.service.impl.BikeInstanceServiceImpl;
import be.kdg.swiftby.service.intf.BikeOwnerService;
import be.kdg.swiftby.service.intf.BikeService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bikeowners")
public class BikeOwnerApiController {
    private final BikeInstanceServiceImpl bikeInstanceServiceImpl;
    BikeOwnerService bikeOwnerService;
    BikeOwnerApiMapper bikeOwnerApiMapper;
    BikeService bikeService;
    BikeMapperApi bikeMapperApi;

    public BikeOwnerApiController(BikeOwnerService bikeOwnerService, BikeOwnerApiMapper bikeOwnerApiMapper, BikeMapperApi bikeMapperApi, BikeInstanceServiceImpl bikeInstanceServiceImpl) {
        this.bikeOwnerService = bikeOwnerService;
        this.bikeOwnerApiMapper = bikeOwnerApiMapper;
        this.bikeService = bikeService;
        this.bikeMapperApi = bikeMapperApi;
        this.bikeInstanceServiceImpl = bikeInstanceServiceImpl;
    }

    @GetMapping("")
    public ResponseEntity<List<BikeOwnerApiResponseDto>> getAll() {
        return ResponseEntity.ok(bikeOwnerApiMapper.toBikeOwnerDtoList(bikeOwnerService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BikeOwnerApiResponseDto> getBikeOwnerById(@PathVariable Long id) {
        return ResponseEntity.ok(bikeOwnerApiMapper.toBikeOwnerDto(bikeOwnerService.getById(id)));
    }
    @GetMapping("/by-email")
    public ResponseEntity<BikeOwnerApiResponseDto> getBikeOwnerByEmail(@RequestParam String email) {
        return ResponseEntity.ok(bikeOwnerApiMapper.toBikeOwnerDto(bikeOwnerService.getByEmail(email)));
    }

    @GetMapping("/search")
    public ResponseEntity<List<BikeOwnerApiResponseDto>> searchOwners(@RequestParam String email) {
        List<BikeOwner> owners = bikeOwnerService.searchOwnersByEmail(email);
        return ResponseEntity.ok(owners.stream()
                .map(bikeOwnerApiMapper::toBikeOwnerDto).toList());
    }

    @PostMapping
    public ResponseEntity<BikeOwnerApiResponseDto> createBikeOwner(@RequestBody BikeOwnerRequestDto request) {
        BikeOwner owner = bikeOwnerService.save(request.email(), request.firstName(), request.lastName(), request.phoneNumber());
        return ResponseEntity.ok(bikeOwnerApiMapper.toBikeOwnerDto(owner));
    }

    @GetMapping("/{bikeOwnerId}/bikes")
    public ResponseEntity<List<BikeApiResponseDto>> getBikesByOwner(@PathVariable Long bikeOwnerId) {
        List<BikeInstance> bikes = bikeInstanceServiceImpl.getByBikeOwnerId(bikeOwnerId);
        if (bikes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(
                bikes.stream()
                        .map(bike -> bikeMapperApi.toBikeDto(bike))
                        .toList()
        );
    }

    @DeleteMapping("/{bikeOwnerId}")
    public ResponseEntity<Void> removeBikeOwner(@PathVariable Long bikeOwnerId) {

        bikeOwnerService.remove(bikeOwnerId);
        return ResponseEntity.noContent().build();
    }

}
