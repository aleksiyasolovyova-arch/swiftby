package be.kdg.swiftby.domain.bike;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Motor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String engineType;
    private String gearType;
    private int maxPower;
    private int nominalPower;
    private int torque;


}
