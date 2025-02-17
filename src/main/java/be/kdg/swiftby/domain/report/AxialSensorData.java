package be.kdg.swiftby.domain.report;

import lombok.Data;

@Data
public class AxialSensorData {
    private Long id;
    private double horizontalInclination;
    private double verticalInclination;
}
