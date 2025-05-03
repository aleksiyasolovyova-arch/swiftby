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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

   @Column(nullable = false, unique = true)
   private UUID testId;


    private String lightsStatus;
    private String brakesStatus;
    private String displayStatus;
    private String hornStatus;
    private String motorStatus;
    private String batteryStatus;
}