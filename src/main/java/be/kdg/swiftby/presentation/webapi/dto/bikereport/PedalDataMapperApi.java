package be.kdg.swiftby.presentation.webapi.dto.bikereport;

import be.kdg.swiftby.domain.report.PedalData;
import be.kdg.swiftby.service.dto.PedalDataDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface PedalDataMapperApi {
    PedalDataDto toPedalDataDto(PedalData pedalData);

}
