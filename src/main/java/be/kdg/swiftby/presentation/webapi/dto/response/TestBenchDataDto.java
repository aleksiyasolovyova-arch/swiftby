package be.kdg.swiftby.presentation.webapi.dto.response;

public record TestBenchDataDto(
    double rollerTorque,
    double loadCell,
    double rol,
    int loadPower,
    boolean statusPlug,
    Long testBenchId
) {}
