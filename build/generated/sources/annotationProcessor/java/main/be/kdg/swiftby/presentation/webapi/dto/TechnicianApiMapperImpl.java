package be.kdg.swiftby.presentation.webapi.dto;

import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.domain.testEnv.Technician;
import be.kdg.swiftby.presentation.webapi.dto.response.TechnicianApiRequestDto;
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
public class TechnicianApiMapperImpl implements TechnicianApiMapper {

    @Override
    public List<TechnicianApiRequestDto> toTechnicianApiRequestDtoList(List<Technician> technicians) {
        if ( technicians == null ) {
            return null;
        }

        List<TechnicianApiRequestDto> list = new ArrayList<TechnicianApiRequestDto>( technicians.size() );
        for ( Technician technician : technicians ) {
            list.add( toTechnicianApiRequestDto( technician ) );
        }

        return list;
    }

    @Override
    public TechnicianApiRequestDto toTechnicianApiRequestDto(Technician technician) {
        if ( technician == null ) {
            return null;
        }

        Long facilityId = null;
        Long id = null;
        String email = null;
        String password = null;
        String firstName = null;
        String lastName = null;
        String phoneNumber = null;

        facilityId = technicianFacilityId( technician );
        id = technician.getId();
        email = technician.getEmail();
        password = technician.getPassword();
        firstName = technician.getFirstName();
        lastName = technician.getLastName();
        phoneNumber = technician.getPhoneNumber();

        TechnicianApiRequestDto technicianApiRequestDto = new TechnicianApiRequestDto( facilityId, id, email, password, firstName, lastName, phoneNumber );

        return technicianApiRequestDto;
    }

    private Long technicianFacilityId(Technician technician) {
        Facility facility = technician.getFacility();
        if ( facility == null ) {
            return null;
        }
        return facility.getId();
    }
}
