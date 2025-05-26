package be.kdg.swiftby.presentation.webapi.dto.request;

import be.kdg.swiftby.service.dto.MotorDto;

public record MotorRequestDto (
        String engineType,
        Integer maxPower,
        Integer nominalPower,
        String gearType,
        Integer torque
){
    public MotorDto toMotorDto() {
        return new MotorDto(engineType, gearType, maxPower, nominalPower, torque);
    }
}
