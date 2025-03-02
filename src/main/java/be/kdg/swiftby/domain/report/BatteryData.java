package be.kdg.swiftby.domain.report;

import be.kdg.swiftby.service.dto.BatteryDataDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Entity
@Data
public class BatteryData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean chargeStatus;
    private double current;
    private double voltage;
    private double capacity;
    private double temperature;

    public BatteryData() {
    }

    public BatteryData(BatteryDataDto dto) {
        this.chargeStatus = dto.chargeStatus();
        this.current = dto.current();
        this.voltage = dto.voltage();
        this.capacity = dto.capacity();
        this.temperature = dto.temperature();
    }
}
