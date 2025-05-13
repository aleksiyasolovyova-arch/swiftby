package be.kdg.swiftby.domain.report;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

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