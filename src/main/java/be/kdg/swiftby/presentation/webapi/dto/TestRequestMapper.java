package be.kdg.swiftby.presentation.webapi.dto;

import be.kdg.swiftby.presentation.webapi.dto.request.TestRequestDto;
import be.kdg.swiftby.presentation.webapi.dto.response.TestResponseDto;
import be.kdg.swiftby.service.dto.api.dto.TestDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TestRequestMapper {
    TestRequestDto toTestRequestDto(TestDto testDto);

    TestResponseDto toTestResponseDto(TestDto testDto);
}
