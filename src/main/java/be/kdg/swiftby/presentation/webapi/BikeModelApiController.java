package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.domain.bike.BIKE_SIZE;
import be.kdg.swiftby.domain.bike.BikeInstance;
import be.kdg.swiftby.domain.bike.BikeModel;
import be.kdg.swiftby.domain.bike.POWERTRAIN;
import be.kdg.swiftby.presentation.webapi.dto.bikereport.BikeMapperApi;
import be.kdg.swiftby.presentation.webapi.dto.request.BikeRequestDto;
import be.kdg.swiftby.presentation.webapi.dto.response.BikeApiResponseDto;
import be.kdg.swiftby.service.dto.BikeModelDto;
import be.kdg.swiftby.service.dto.MotorDto;
import be.kdg.swiftby.service.dto.mapper.BikeModelMappper;
import be.kdg.swiftby.service.impl.BikeOwnershipServiceImpl;
import be.kdg.swiftby.service.intf.BikeInstanceService;
import be.kdg.swiftby.service.intf.BikeModelService;
import be.kdg.swiftby.service.intf.BikeOwnershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/api/bike-models")
public class BikeModelApiController {

    private final BikeModelService bikeModelService;
    private final BikeModelMappper bikeModelMapper;
    private final BikeMapperApi bikeMapperApi;
    private final BikeInstanceService bikeInstanceService;
    private final BikeOwnershipService bikeOwnershipService;

    public BikeModelApiController(BikeModelService bikeModelService, BikeModelMappper bikeModelMapper, BikeMapperApi bikeMapperApi, BikeInstanceService bikeInstanceService, BikeOwnershipService bikeOwnershipService) {
        this.bikeModelService = bikeModelService;
        this.bikeModelMapper = bikeModelMapper;
        this.bikeMapperApi = bikeMapperApi;
        this.bikeInstanceService = bikeInstanceService;
        this.bikeOwnershipService = bikeOwnershipService;
    }

    @GetMapping
    public ResponseEntity<List<BikeModelDto>> getAllBikeModels() {
        List<BikeModel> models = bikeModelService.getAll();
        return ResponseEntity.ok(models.stream().map(bikeModelMapper::toBikeModelDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BikeModelDto> getBikeModelById(@PathVariable Long id) {
        BikeModel model = bikeModelService.getById(id);
        return ResponseEntity.ok(bikeModelMapper.toBikeModelDto(model));
    }
//
//    @PostMapping
//    public ResponseEntity<BikeApiResponseDto> saveBikeModel(@RequestBody BikeRequestDto dto) {
//        BikeModelDto modelDto = new BikeModelDto(
//                dto.brand(),
//                dto.type(),
//                dto.powertrain(),
//                dto.bikeSize(),
//                dto.maxSupport(),
//                new MotorDto(
//                        dto.motor().engineType(),
//                        dto.motor().gearType(),
//                        dto.motor().maxPower(),
//                        dto.motor().nominalPower(),
//                        dto.motor().torque()
//                ),
//                dto.batteryCapacity()
//        );
//
//
//        BikeModel saved = bikeModelService.save(modelDto);
//        return ResponseEntity.ok(bikeMapperApi.toBikeDto(saved));
//    }

    @PostMapping
    public ResponseEntity<BikeApiResponseDto> createBikeWithModelAndOwnership(@RequestBody BikeRequestDto dto) {
        // (handles brand+type check)
        BikeModel model = bikeModelService.save(dto.toBikeModelDto());

        BikeInstance instance = bikeInstanceService.createInstance(dto.chassisNumber(), model.getId());

        bikeOwnershipService.assignOwnerToBike(dto.ownerId(), instance.getId());

        return ResponseEntity.ok(bikeMapperApi.toBikeDto(model));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModel(@PathVariable Long id) {
        bikeModelService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sizes")
    public List<String> getBikeSizes() {
        return Arrays.stream(BIKE_SIZE.values()).map(Enum::name).toList();
    }

    @GetMapping("/powertrains")
    public List<String> getPowertrains() {
        return Arrays.stream(POWERTRAIN.values()).map(Enum::name).toList();
    }

    @GetMapping("/motor-engine-type/{engineType}")
    public ResponseEntity<List<BikeModelDto>> getByEngineType(@PathVariable String engineType) {
        return ResponseEntity.ok(
                bikeModelService.findByMotorEngineType(engineType).stream()
                        .map(bikeModelMapper::toBikeModelDto)
                        .toList()
        );
    }
}



//@RestController
//@RequestMapping("/api/bikes")
//public class BikeApiController {
//
//    private final BikeService bikeService;
//    private final BikeModelMappper bikeModelDto;
//    private final BikeMapperApi bikeMapperApi;
//
//    public BikeApiController(BikeService bikeService, BikeModelMappper bikeModelDto, BikeMapperApi bikeMapperApi) {
//        this.bikeService = bikeService;
//        this.bikeModelDto = bikeModelDto;
//        this.bikeMapperApi = bikeMapperApi;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<be.kdg.swiftby.service.dto.BikeModelDto>> getAllBikes() {
//        List<BikeModel> bikeModels = bikeService.getAll();
//        return ResponseEntity.ok(bikeModels.stream()
//                .map(bikeModelDto::toBikeDto)
//                .toList());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<be.kdg.swiftby.service.dto.BikeModelDto> getBikeById(@PathVariable Long id) {
//        BikeModel bikeModel = bikeService.getById(id);
//        return ResponseEntity.ok(bikeModelDto.toBikeDto(bikeModel));
//    }
//
//    @GetMapping("/owner/{bikeOwnerId}")
//    public ResponseEntity<List<BikeApiResponseDto>> getBikesByOwner(@PathVariable Long bikeOwnerId) {
//        List<BikeModel> bikeModels = bikeService.getByBikeOwnerId(bikeOwnerId);
//        if (bikeModels.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(bikeModels.stream().map(bikeMapperApi::toBikeDto).toList());
//    }
//
//
//    @GetMapping("/motor-engine-type/{engineType}")
//    public ResponseEntity<List<be.kdg.swiftby.service.dto.BikeModelDto>> getBikesByMotorEngineType(@PathVariable String engineType) {
//        List<BikeModel> bikeModels = bikeService.findByMotorEngineType(engineType);
//        return ResponseEntity.ok(bikeModels.stream()
//                .map(bikeModelDto::toBikeDto)
//                .toList());
//    }
//
//    @PostMapping
//    public ResponseEntity<BikeApiResponseDto> saveBike(@RequestBody BikeRequestDto bikeRequestDto) {
//        System.out.println("Received BikeRequestDto: " + bikeRequestDto);
//
//        MotorRequestDto motorDto = bikeRequestDto.motor();
//
//        be.kdg.swiftby.service.dto.BikeModelDto bikeModelDto = new be.kdg.swiftby.service.dto.BikeModelDto(
//                bikeRequestDto.brand(),
//                bikeRequestDto.type(),
//                bikeRequestDto.chassisNumber(),
//                bikeRequestDto.powertrain(),
//                bikeRequestDto.bikeSize(),
//                bikeRequestDto.maxSupport(),
//                new MotorDto(motorDto.engineType(), motorDto.gearType(), motorDto.maxPower(), motorDto.nominalPower(), motorDto.torque()),
//                bikeRequestDto.batteryCapacity()
//        );
//
//        BikeModel savedBikeModel = bikeService.save(bikeModelDto);
//        return ResponseEntity.ok(bikeMapperApi.toBikeDto(savedBikeModel));
//    }
//
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteBike(@PathVariable Long id) {
//        bikeService.remove(id);
//        return ResponseEntity.noContent().build();
//    }
//    @GetMapping("/sizes")
//    public List<String> getBikeSizes() {
//        return Arrays.stream(BIKE_SIZE.values())
//                .map(Enum::name)
//                .toList();
//    }
//    @GetMapping("/powertrains")
//    public List<String> getPowertrains() {
//        return Arrays.stream(POWERTRAIN.values())
//                .map(Enum::name)
//                .toList();
//    }
//}
//

