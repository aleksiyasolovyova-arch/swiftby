package be.kdg.swiftby.presentation.webapi.dto;

import be.kdg.swiftby.domain.testEnv.SuperAdmin;
import be.kdg.swiftby.presentation.webapi.dto.request.SuperAdminApiRequestDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-28T21:18:33+0100",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class SuperAdminApiMapperImpl implements SuperAdminApiMapper {

    @Override
    public List<SuperAdminApiRequestDto> toSuperAdminApiRequestDtoList(List<SuperAdmin> superAdmins) {
        if ( superAdmins == null ) {
            return null;
        }

        List<SuperAdminApiRequestDto> list = new ArrayList<SuperAdminApiRequestDto>( superAdmins.size() );
        for ( SuperAdmin superAdmin : superAdmins ) {
            list.add( toSuperAdminApiRequestDto( superAdmin ) );
        }

        return list;
    }

    @Override
    public SuperAdminApiRequestDto toSuperAdminApiRequestDto(SuperAdmin superAdmin) {
        if ( superAdmin == null ) {
            return null;
        }

        Long id = null;
        String email = null;
        String password = null;
        String firstName = null;
        String lastName = null;
        String phoneNumber = null;

        id = superAdmin.getId();
        email = superAdmin.getEmail();
        password = superAdmin.getPassword();
        firstName = superAdmin.getFirstName();
        lastName = superAdmin.getLastName();
        phoneNumber = superAdmin.getPhoneNumber();

        SuperAdminApiRequestDto superAdminApiRequestDto = new SuperAdminApiRequestDto( id, email, password, firstName, lastName, phoneNumber );

        return superAdminApiRequestDto;
    }
}
