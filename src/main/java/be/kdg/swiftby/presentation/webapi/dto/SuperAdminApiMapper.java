package be.kdg.swiftby.presentation.webapi.dto;

import be.kdg.swiftby.domain.testEnv.SuperAdmin;
import be.kdg.swiftby.presentation.webapi.dto.response.SuperAdminApiResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface SuperAdminApiMapper {
    List<SuperAdminApiResponseDto> toSuperAdminApiRequestDtoList(List<SuperAdmin> superAdmins);

    SuperAdminApiResponseDto toSuperAdminApiRequestDto(SuperAdmin superAdmin);
}
