package be.kdg.swiftby.service.dto.mapper;

import be.kdg.swiftby.domain.bike.Motor;
import be.kdg.swiftby.service.dto.MotorDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-28T21:18:33+0100",
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
}
