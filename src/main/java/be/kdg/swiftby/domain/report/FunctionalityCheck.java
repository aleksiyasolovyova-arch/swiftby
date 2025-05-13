package be.kdg.swiftby.domain.report;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FunctionalityCheck {
    @Id
    @GeneratedValue
    private Long id;

    private String lightsStatus;
    private String brakesStatus;
    private String displayStatus;
    private String hornStatus;
    private String motorStatus;
    private String batteryStatus;
}