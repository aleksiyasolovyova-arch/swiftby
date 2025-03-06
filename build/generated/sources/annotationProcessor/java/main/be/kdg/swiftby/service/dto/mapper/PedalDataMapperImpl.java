package be.kdg.swiftby.service.dto.mapper;

import be.kdg.swiftby.domain.report.PedalData;
import be.kdg.swiftby.service.dto.PedalDataDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-04T15:39:05+0100",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class PedalDataMapperImpl implements PedalDataMapper {

    @Override
    public PedalData toPedalData(PedalDataDto pedalDataDto) {
        if ( pedalDataDto == null ) {
            return null;
        }

        PedalData pedalData = new PedalData();

        pedalData.setTorqueCrank( pedalDataDto.torqueCrank() );
        pedalData.setCadence( pedalDataDto.cadence() );

        return pedalData;
    }
}
