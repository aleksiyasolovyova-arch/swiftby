package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.bike.Bike;
import be.kdg.swiftby.domain.bike.Motor;
import be.kdg.swiftby.repository.bike.BikeRepository;
import be.kdg.swiftby.repository.bike.MotorRepository;
import be.kdg.swiftby.service.dto.BikeDto;
import be.kdg.swiftby.service.dto.mapper.BikeMapper;
import be.kdg.swiftby.service.dto.mapper.MotorMapper;
import be.kdg.swiftby.service.intf.BikeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeServiceImpl implements BikeService {

    private final BikeRepository bikeRepository;
    private final BikeMapper bikeMapper;
    private final MotorRepository motorRepository;
    private final MotorMapper motorMapper;



    public BikeServiceImpl(BikeRepository bikeRepository, BikeMapper bikeMapper, MotorRepository motorRepository, MotorMapper motorMapper) {
        this.bikeRepository = bikeRepository;
        this.bikeMapper = bikeMapper;
        this.motorRepository = motorRepository;
        this.motorMapper = motorMapper;
    }


    @Override
    public List<Bike> getAll() {
        return bikeRepository.findAll();
    }

    @Override
    public Bike getById(Long id) {
        return bikeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bike not found with id " + id));
    }

    @Override
    public Bike save(BikeDto bikeDto) {
        Bike bike = bikeMapper.toBike(bikeDto);

        if (bikeDto.motor() != null) {
            Motor motor = motorMapper.toMotor(bikeDto.motor());
            Motor existingMotor = motorRepository
                    .findByEngineType(motor.getEngineType())
                    .orElseGet(() -> motorRepository.save(motor));
            bike.setMotor(existingMotor);
        } else {
            bike.setMotor(null);
        }

        return bikeRepository.save(bike);
    }

    @Override
    public List<Bike> findByMotorEngineType(String engineType) {
        return bikeRepository.findByMotorEngineType(engineType);
    }
    @Override
    public void remove(Long id) {
        if (bikeRepository.existsById(id)) {
            bikeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Bike not found with id " + id);
        }
    }
}
