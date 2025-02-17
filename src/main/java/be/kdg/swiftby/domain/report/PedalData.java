package be.kdg.swiftby.domain.report;

import lombok.Data;

@Data
public class PedalData {
    private Long id;
    private double torqueCrank;
    private int cadence;
}
