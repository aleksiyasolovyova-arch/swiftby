package be.kdg.swiftby.domain.report;

import be.kdg.swiftby.service.dto.MotorDataDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class MotorData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double engine;
    private double enginePower;

    public MotorData() {
    }

    public MotorData(MotorDataDto dto) {
        this.engine = dto.engine();
        this.enginePower = dto.enginePower();
    }
}
