package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.domain.bike.BIKE_SIZE;
import be.kdg.swiftby.domain.bike.Bike;
import be.kdg.swiftby.domain.bike.POWERTRAIN;
import be.kdg.swiftby.presentation.webapi.dto.bikereport.BikeMapperApi;
import be.kdg.swiftby.presentation.webapi.dto.request.BikeRequestDto;
import be.kdg.swiftby.presentation.webapi.dto.request.MotorRequestDto;
import be.kdg.swiftby.presentation.webapi.dto.response.BikeApiResponseDto;
import be.kdg.swiftby.service.dto.BikeDto;
import be.kdg.swiftby.service.dto.MotorDto;
import be.kdg.swiftby.service.dto.mapper.BikeMapper;
import be.kdg.swiftby.service.intf.BikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/bikes")
public class BikeApiController {

    private final BikeService bikeService;
    private final BikeMapper bikeMapper;
    private final BikeMapperApi bikeMapperApi;

    public BikeApiController(BikeService bikeService, BikeMapper bikeMapper, BikeMapperApi bikeMapperApi) {
        this.bikeService = bikeService;
        this.bikeMapper = bikeMapper;
        this.bikeMapperApi = bikeMapperApi;
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

    @GetMapping("/{bikeOwnerId}")
    public ResponseEntity<List<BikeApiResponseDto>> getBikesByOwner(@PathVariable Long bikeOwnerId) {
        List<Bike> bikes = bikeService.getByBikeOwnerId(bikeOwnerId);
        if (bikes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bikes.stream().map(bikeMapperApi::toBikeDto).toList());
    }


    @GetMapping("/motor-engine-type/{engineType}")
    public ResponseEntity<List<BikeDto>> getBikesByMotorEngineType(@PathVariable String engineType) {
        List<Bike> bikes = bikeService.findByMotorEngineType(engineType);
        return ResponseEntity.ok(bikes.stream()
                .map(bikeMapper::toBikeDto)
                .toList());
    }

    @PostMapping
    public ResponseEntity<BikeApiResponseDto> saveBike(@RequestBody BikeRequestDto bikeRequestDto) {
        System.out.println("Received BikeRequestDto: " + bikeRequestDto);

        MotorRequestDto motorDto = bikeRequestDto.motor();

        BikeDto bikeDto = new BikeDto(
                bikeRequestDto.brand(),
                bikeRequestDto.type(),
                bikeRequestDto.chassisNumber(),
                bikeRequestDto.powertrain(),
                bikeRequestDto.bikeSize(),
                bikeRequestDto.maxSupport(),
                new MotorDto(motorDto.engineType(), motorDto.gearType(), motorDto.maxPower(), motorDto.nominalPower(), motorDto.torque()),
                bikeRequestDto.batteryCapacity()
        );

        Bike savedBike = bikeService.save(bikeDto);
        return ResponseEntity.ok(bikeMapperApi.toBikeDto(savedBike));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBike(@PathVariable Long id) {
        bikeService.remove(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/sizes")
    public List<String> getBikeSizes() {
        return Arrays.stream(BIKE_SIZE.values())
                .map(Enum::name)
                .toList();
    }
    @GetMapping("/powertrains")
    public List<String> getPowertrains() {
        return Arrays.stream(POWERTRAIN.values())
                .map(Enum::name)
                .toList();
    }
}


