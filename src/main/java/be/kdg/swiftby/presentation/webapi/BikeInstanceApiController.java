package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.domain.bike.BikeInstance;
import be.kdg.swiftby.presentation.webapi.dto.BikeInstanceDto;
import be.kdg.swiftby.presentation.webapi.dto.BikeInstanceMapper;
import be.kdg.swiftby.presentation.webapi.dto.request.BikeInstanceRequestDto;
import be.kdg.swiftby.service.intf.BikeInstanceService;
import be.kdg.swiftby.service.intf.BikeOwnershipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bike-instances")
@RequiredArgsConstructor
public class BikeInstanceApiController {

    private final BikeInstanceService bikeInstanceService;
    private final BikeOwnershipService bikeOwnershipService;
    private final BikeInstanceMapper bikeInstanceMapper;

    @GetMapping
    public ResponseEntity<List<BikeInstanceDto>> getAll() {
        List<BikeInstance> bikes = bikeInstanceService.getAll();
        return ResponseEntity.ok(bikes.stream()
                .map(bikeInstanceMapper::toDto)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BikeInstanceDto> getById(@PathVariable Long id) {
        BikeInstance bike = bikeInstanceService.getById(id);
        return ResponseEntity.ok(bikeInstanceMapper.toDto(bike));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<BikeInstanceDto>> getByOwner(@PathVariable Long ownerId) {
        List<BikeInstance> bikes = bikeInstanceService.getByBikeOwnerId(ownerId);
        if (bikes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bikes.stream()
                .map(bikeInstanceMapper::toDto)
                .toList());
    }

    @PostMapping
    public ResponseEntity<BikeInstanceDto> create(@RequestBody @Valid BikeInstanceRequestDto dto) {
        BikeInstance bike = bikeInstanceService.createInstance(dto.chassisNumber(), dto.modelId());
        return ResponseEntity.ok(bikeInstanceMapper.toDto(bike));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bikeInstanceService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{instanceId}/assign-owner/{ownerId}")
    public ResponseEntity<Void> assignOwner(@PathVariable Long instanceId, @PathVariable Long ownerId) {
        bikeOwnershipService.assignOwnerToBike(ownerId, instanceId);
        return ResponseEntity.ok().build();
    }
}
