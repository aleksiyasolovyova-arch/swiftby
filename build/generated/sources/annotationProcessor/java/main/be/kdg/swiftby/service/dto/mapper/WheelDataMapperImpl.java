package be.kdg.swiftby.service.dto.mapper;

import be.kdg.swiftby.domain.report.WheelData;
import be.kdg.swiftby.service.dto.WheelDataDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-02T15:38:09+0100",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class WheelDataMapperImpl implements WheelDataMapper {

    @Override
    public WheelData toWheelData(WheelDataDto wheelDataDto) {
        if ( wheelDataDto == null ) {
            return null;
        }

        WheelData wheelData = new WheelData();

        wheelData.setSpeed( wheelDataDto.speed() );
        wheelData.setPower( wheelDataDto.power() );

        return wheelData;
    }
}
