package be.kdg.swiftby.service.dto.mapper;

import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.service.dto.FacilityDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-06T14:51:53+0100",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class FacilityMapperImpl implements FacilityMapper {

    @Override
    public Facility toFacility(FacilityDto facilityApiResponseDto) {
        if ( facilityApiResponseDto == null ) {
            return null;
        }

        Facility facility = new Facility();

        facility.setId( facilityApiResponseDto.id() );
        facility.setName( facilityApiResponseDto.name() );
        facility.setEmail( facilityApiResponseDto.email() );
        facility.setCountry( facilityApiResponseDto.country() );
        facility.setCity( facilityApiResponseDto.city() );
        facility.setZipCode( facilityApiResponseDto.zipCode() );
        facility.setStreet( facilityApiResponseDto.street() );
        facility.setStreetNumber( facilityApiResponseDto.streetNumber() );
        facility.setAddressExtra( facilityApiResponseDto.addressExtra() );

        return facility;
    }
}
