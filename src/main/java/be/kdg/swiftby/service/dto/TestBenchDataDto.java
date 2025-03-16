package be.kdg.swiftby.service.dto;

public record TestBenchDataDto(
        double rollerTorque,
        double loadCell,
        double rol,
        int loadPower,
        boolean statusPlug,
        Long testBenchDataId
) {
}
