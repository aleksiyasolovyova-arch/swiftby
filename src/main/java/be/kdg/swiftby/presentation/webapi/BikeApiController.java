package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.domain.bike.Bike;
import be.kdg.swiftby.service.dto.BikeDto;
import be.kdg.swiftby.service.dto.mapper.BikeMapper;
import be.kdg.swiftby.service.intf.BikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bikes")
public class BikeApiController {

    private final BikeService bikeService;
    private final BikeMapper bikeMapper;

    public BikeApiController(BikeService bikeService, BikeMapper bikeMapper) {
        this.bikeService = bikeService;
        this.bikeMapper = bikeMapper;
    }

    @GetMapping
    public ResponseEntity<List<BikeDto>> getAllBikes() {
        List<Bike> bikes = bikeService.getAll();
        return ResponseEntity.ok(bikes.stream()
                .map(bikeMapper::toBikeDto)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BikeDto> getBikeById(@PathVariable Long id) {
        Bike bike = bikeService.getById(id);
        return ResponseEntity.ok(bikeMapper.toBikeDto(bike));
    }

    @GetMapping("/motor-engine-type/{engineType}")
    public ResponseEntity<List<BikeDto>> getBikesByMotorEngineType(@PathVariable String engineType) {
        List<Bike> bikes = bikeService.findByMotorEngineType(engineType);
        return ResponseEntity.ok(bikes.stream()
                .map(bikeMapper::toBikeDto)
                .toList());
    }

    @PostMapping
    public ResponseEntity<BikeDto> saveBike(@RequestBody BikeDto bikeDto) {
        Bike savedBike = bikeService.save(bikeDto);
        return ResponseEntity.ok(bikeMapper.toBikeDto(savedBike));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBike(@PathVariable Long id) {
        bikeService.remove(id);
        return ResponseEntity.noContent().build();
    }
}


