package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.bike.Motor;
import be.kdg.swiftby.domain.testEnv.Administrator;
import be.kdg.swiftby.service.dto.MotorDto;

import java.util.List;

public interface MotorService {
    List<Motor> getAll();
    Motor getById(Long id);
    Motor save(MotorDto motorDto);
    void remove(Long id);
}
