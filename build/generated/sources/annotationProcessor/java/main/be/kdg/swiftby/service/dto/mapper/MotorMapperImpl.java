package be.kdg.swiftby.service.dto.mapper;

import be.kdg.swiftby.domain.bike.Motor;
import be.kdg.swiftby.service.dto.MotorDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-06T14:29:48+0100",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class MotorMapperImpl implements MotorMapper {

    @Override
    public Motor toMotor(MotorDto motorDto) {
        if ( motorDto == null ) {
            return null;
        }

        Motor motor = new Motor();

        motor.setEngineType( motorDto.engineType() );
        motor.setGearType( motorDto.gearType() );
        motor.setMaxPower( motorDto.maxPower() );
        motor.setNominalPower( motorDto.nominalPower() );
        motor.setTorque( motorDto.torque() );

        return motor;
    }

    @Override
    public MotorDto toMotorDto(Motor motor) {
        if ( motor == null ) {
            return null;
        }

        String engineType = null;
        String gearType = null;
        int maxPower = 0;
        int nominalPower = 0;
        int torque = 0;

        engineType = motor.getEngineType();
        gearType = motor.getGearType();
        if ( motor.getMaxPower() != null ) {
            maxPower = motor.getMaxPower();
        }
        if ( motor.getNominalPower() != null ) {
            nominalPower = motor.getNominalPower();
        }
        if ( motor.getTorque() != null ) {
            torque = motor.getTorque();
        }

        MotorDto motorDto = new MotorDto( engineType, gearType, maxPower, nominalPower, torque );

        return motorDto;
    }
}
