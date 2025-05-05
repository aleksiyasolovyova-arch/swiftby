package be.kdg.swiftby.domain.report;

import be.kdg.swiftby.service.dto.PedalDataDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Entity
@Data
public class PedalData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double torqueCrank;
    private double cadence;

    public PedalData() {
    }

    public PedalData(PedalDataDto dto) {
        this.cadence = dto.cadence();
        this.torqueCrank = dto.torqueCrank();
    }
}
