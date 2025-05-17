package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.bike.BikeModel;
import be.kdg.swiftby.domain.bike.Motor;
import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.repository.bike.MotorRepository;
import be.kdg.swiftby.service.dto.BikeModelDto;
import be.kdg.swiftby.service.dto.mapper.BikeModelMappper;
import be.kdg.swiftby.service.dto.mapper.MotorMapper;
import be.kdg.swiftby.service.intf.BikeModelService;
import org.springframework.stereotype.Service;
import be.kdg.swiftby.repository.bike.BikeModelRepository;
import java.util.List;
import java.util.Optional;


@Service
public class BikeModelServiceImpl implements BikeModelService {
    private final BikeModelRepository bikeModelRepository;
    private final MotorRepository motorRepository;
    private final BikeModelMappper mapper;
    private final MotorMapper motorMapper;

    public BikeModelServiceImpl(
            BikeModelRepository bikeModelRepository,
            MotorRepository motorRepository,
            BikeModelMappper mapper,
            MotorMapper motorMapper
    ) {
        this.bikeModelRepository = bikeModelRepository;
        this.motorRepository = motorRepository;
        this.mapper = mapper;
        this.motorMapper = motorMapper;
    }

    @Override
    public List<BikeModel> getAll() {
        return bikeModelRepository.findAll();
    }

    @Override
    public BikeModel save(BikeModelDto dto) {
        Optional<BikeModel> existing = bikeModelRepository
                .findByBrandIgnoreCaseAndTypeIgnoreCase(dto.brand(), dto.type());

        if (existing.isPresent()) return existing.get();

        Motor motor = motorMapper.toMotor(dto.motor());
        Motor savedMotor = motorRepository.findByEngineType(motor.getEngineType())
                .orElseGet(() -> motorRepository.save(motor));

        BikeModel model = mapper.toBikeModel(dto);
        model.setMotor(savedMotor);
        return bikeModelRepository.save(model);
    }


    @Override
    public List<BikeModel> findByMotorEngineType(String engineType) {
        return bikeModelRepository.findByMotorEngineType(engineType);
    }

    @Override
    public void remove(Long id) {
        bikeModelRepository.deleteById(id);
    }

    @Override
    public BikeModel getById(Long id) {
        return bikeModelRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forBike(id));
    }

}

//@Service
//public class BikeModelServiceImpl implements BikeService {
//
//    private final BikeMapper bikeMapper;
//    private final MotorRepository motorRepository;
//    private final MotorMapper motorMapper;
//    private final BikeOwnershipRepository bikeOwnershipRepository;
//    private final BikeModelRepository bikeModelRepository;
//
//
//    public BikeModelServiceImpl(BikeMapper bikeMapper, MotorRepository motorRepository, MotorMapper motorMapper, BikeOwnershipRepository bikeOwnershipRepository, BikeModelRepository bikeModelRepository) {
//        this.bikeMapper = bikeMapper;
//        this.motorRepository = motorRepository;
//        this.motorMapper = motorMapper;
//        this.bikeOwnershipRepository = bikeOwnershipRepository;
//        this.bikeModelRepository = bikeModelRepository;
//    }
//
//
//    @Override
//    public List<BikeModel> getAll() {
//        return bikeModelRepository.findAll();
//    }
//
//    @Override
//    public BikeModel getById(Long id) {
//        return bikeModelRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Bike not found with id " + id));
//    }
//
//    @Override
//    public BikeModel getByIdWithOwner(Long id) {
//        return bikeModelRepository.findByIdWithOwnerships(id)
//                .orElseThrow(() -> new RuntimeException("Bike not found with id " + id));
//    }
//
//
//    @Override
//    public List<BikeModel> getByBikeOwnerId(Long bikeOwnerId) {
//        List<BikeOwnership> ownerships = bikeOwnershipRepository.findByOwnerId(bikeOwnerId);
//        return ownerships.stream()
//                .map(BikeOwnership::getBikeModel)
//                .toList();
//    }
//
//
//
//    @Override
//    public BikeModel save(BikeDto bikeDto) {
//        Optional<BikeModel> existingBike = bikeModelRepository.findBikeByChassisNumber(bikeDto.chassisNumber());
//
//        BikeModel bikeModel = bikeMapper.toBike(bikeDto);
//
//        if (existingBike.isPresent()) {
//            System.out.println("Bike already exists, skip adding");
//            // TODO: show this in the front end
//        }
//        if (bikeDto.motor() != null) {
//            Motor motor = motorMapper.toMotor(bikeDto.motor());
//            Motor existingMotor = motorRepository
//                    .findByEngineType(motor.getEngineType())
//                    .orElseGet(() -> motorRepository.save(motor));
//            bikeModel.setMotor(existingMotor);
//        }
////        else {
////            bike.setMotor(null);
////        }
//
//        return bikeModelRepository.save(bikeModel);
//    }
//
//    @Override
//    public List<BikeModel> findByMotorEngineType(String engineType) {
//        return bikeModelRepository.findByMotorEngineType(engineType);
//    }
//    @Override
//    public void remove(Long id) {
//        if (bikeModelRepository.existsById(id)) {
//            bikeModelRepository.deleteById(id);
//        } else {
//            throw new RuntimeException("Bike not found with id " + id);
//        }
//    }
//    @Override
//    public List<BikeModel> getAllByFacilityId(Long facilityId) {
//        return bikeModelRepository.findAllByFacilityId(facilityId);
//    }
//
//}
