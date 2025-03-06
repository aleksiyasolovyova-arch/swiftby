package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.bike.Motor;
import be.kdg.swiftby.repository.bike.MotorRepository;
import be.kdg.swiftby.service.dto.MotorDto;
import be.kdg.swiftby.service.dto.mapper.MotorMapper;
import be.kdg.swiftby.service.intf.MotorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotorServiceImpl implements MotorService {

    private final MotorRepository motorRepository;
    private final MotorMapper motorMapper;

    @Autowired
    public MotorServiceImpl(MotorRepository motorRepository, MotorMapper motorMapper) {
        this.motorRepository = motorRepository;
        this.motorMapper = motorMapper;
    }

    @Override
    public List<Motor> getAll() {
        return motorRepository.findAll();
    }

    @Override
    public Motor getById(Long id) {
        Optional<Motor> motor = motorRepository.findById(id);
        return motor.orElseThrow(() -> new RuntimeException("Motor not found with id " + id));
    }



    @Override
    public Motor save(MotorDto motorDto) {
        Motor motor = motorMapper.toMotor(motorDto);
        return motorRepository.save(motor);
    }

    @Override
    public void remove(Long id) {
        if (motorRepository.existsById(id)) {
            motorRepository.deleteById(id);
        } else {
            throw new RuntimeException("Motor not found with id " + id);
        }

    }
}
