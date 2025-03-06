package be.kdg.swiftby.service.dto.mapper;

import be.kdg.swiftby.domain.report.MotorData;
import be.kdg.swiftby.service.dto.MotorDataDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-04T15:39:05+0100",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class MotorDataMapperImpl implements MotorDataMapper {

    @Override
    public MotorData toMotorData(MotorDataDto motorDataDto) {
        if ( motorDataDto == null ) {
            return null;
        }

        MotorData motorData = new MotorData();

        motorData.setEngine( motorDataDto.engine() );
        motorData.setEnginePower( motorDataDto.enginePower() );

        return motorData;
    }
}
