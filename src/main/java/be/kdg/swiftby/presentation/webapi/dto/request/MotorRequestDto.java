package be.kdg.swiftby.presentation.webapi.dto.request;

public record MotorRequestDto (
        String engineType,
        Integer maxPower,
        Integer nominalPower,
        String gearType,
        Integer torque
){
}
