package be.kdg.swiftby.domain.report;

import lombok.Data;

@Data
public class BatteryData {
    private Long id;
    private boolean chargeStatus;
    private double current;
    private double voltage;
    private double capacity;
    private double temperature;
}
