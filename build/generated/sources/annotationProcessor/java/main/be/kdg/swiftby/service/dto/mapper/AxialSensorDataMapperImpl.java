package be.kdg.swiftby.service.dto.mapper;

import be.kdg.swiftby.domain.report.AxialSensorData;
import be.kdg.swiftby.service.dto.AxialSensorDataDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-04T15:39:05+0100",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class AxialSensorDataMapperImpl implements AxialSensorDataMapper {

    @Override
    public AxialSensorData toAxialSensorData(AxialSensorDataDto axialSensorDataDto) {
        if ( axialSensorDataDto == null ) {
            return null;
        }

        AxialSensorData axialSensorData = new AxialSensorData();

        axialSensorData.setHorizontalInclination( axialSensorDataDto.horizontalInclination() );
        axialSensorData.setVerticalInclination( axialSensorDataDto.verticalInclination() );

        return axialSensorData;
    }
}
