package be.kdg.swiftby.domain.testBench;

import lombok.Data;

@Data
public class TestBenchData {
    private Long id;
    private double rollerTorque;
    private double loadCell;
    private int loadPower;
    private boolean statusPlug;
}
