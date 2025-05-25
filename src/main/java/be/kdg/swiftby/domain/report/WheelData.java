package be.kdg.swiftby.domain.report;
import be.kdg.swiftby.service.dto.WheelDataDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class WheelData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double speed;
    private double power;

    public WheelData(double v, double v1) {
    }

    public WheelData(WheelDataDto dto) {
        this.speed = dto.speed();
        this.power = dto.power();
    }
}
