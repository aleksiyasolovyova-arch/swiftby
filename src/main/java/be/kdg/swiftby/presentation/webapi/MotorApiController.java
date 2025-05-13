package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.domain.bike.Motor;
import be.kdg.swiftby.service.dto.MotorDto;
import be.kdg.swiftby.service.dto.mapper.MotorMapper;
import be.kdg.swiftby.service.intf.MotorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motors")
public class MotorApiController {

    private final MotorService motorService;
    private final MotorMapper motorMapper;


    @Autowired
    public MotorApiController(MotorService motorService, MotorMapper motorMapper) {
        this.motorService = motorService;
        this.motorMapper = motorMapper;
    }

    @GetMapping
    public ResponseEntity<List<MotorDto>> getAllMotors() {
        List<Motor> motors = motorService.getAll();
        List<MotorDto> motorDto = motors.stream()
                .map(motorMapper::toMotorDto)
                .toList();
        return ResponseEntity.ok(motorDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotorDto> getMotorById(@PathVariable Long id) {
        Motor motor = motorService.getById(id);
        return ResponseEntity.ok(motorMapper.toMotorDto(motor));
    }

    @PostMapping
    public ResponseEntity<MotorDto> saveMotor(@RequestBody MotorDto motorDto) {
        Motor savedMotor = motorService.save(motorDto);
        return ResponseEntity.ok(motorMapper.toMotorDto(savedMotor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMotor(@PathVariable Long id) {
        motorService.remove(id);
        return ResponseEntity.noContent().build();
    }

}
