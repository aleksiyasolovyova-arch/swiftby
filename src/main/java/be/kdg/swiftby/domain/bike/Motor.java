package be.kdg.swiftby.domain.bike;

import lombok.Data;

@Data
public class Motor {
    private Long id;
    private String engineType;
    private String gearType;
    private int maxPower;
    private int nominalPower;
    private int torque;
}
