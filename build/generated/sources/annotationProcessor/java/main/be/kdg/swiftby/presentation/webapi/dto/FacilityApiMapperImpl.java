package be.kdg.swiftby.presentation.webapi.dto;

import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.presentation.webapi.dto.response.FacilityApiRequestDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-04T15:23:01+0100",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class FacilityApiMapperImpl implements FacilityApiMapper {

    @Override
    public FacilityApiRequestDto toFacilityApiRequestDto(Facility facility) {
        if ( facility == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String email = null;
        String country = null;
        String city = null;
        String zipCode = null;
        String street = null;
        String streetNumber = null;
        String addressExtra = null;

        id = facility.getId();
        name = facility.getName();
        email = facility.getEmail();
        country = facility.getCountry();
        city = facility.getCity();
        zipCode = facility.getZipCode();
        street = facility.getStreet();
        streetNumber = facility.getStreetNumber();
        addressExtra = facility.getAddressExtra();

        FacilityApiRequestDto facilityApiRequestDto = new FacilityApiRequestDto( id, name, email, country, city, zipCode, street, streetNumber, addressExtra );

        return facilityApiRequestDto;
    }

    @Override
    public List<FacilityApiRequestDto> toFacilityApiRequestDtoList(List<Facility> facilities) {
        if ( facilities == null ) {
            return null;
        }

        List<FacilityApiRequestDto> list = new ArrayList<FacilityApiRequestDto>( facilities.size() );
        for ( Facility facility : facilities ) {
            list.add( toFacilityApiRequestDto( facility ) );
        }

        return list;
    }
}
