package be.kdg.swiftby.service.dto;

public record MotorDto(
        String engineType,
        String gearType,
        int maxPower,
        int nominalPower,
        int torque) {
}
