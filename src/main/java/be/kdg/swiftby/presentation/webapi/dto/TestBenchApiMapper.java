package be.kdg.swiftby.presentation.webapi.dto;

import be.kdg.swiftby.domain.testEnv.TestBench;
import be.kdg.swiftby.presentation.webapi.dto.response.TestBenchApiResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TestBenchApiMapper {
    TestBench toTestBench(TestBenchApiResponseDto testBenchApiResponseDto);

    TestBenchApiResponseDto toTestBenchDto(TestBench testBench);

    List<TestBenchApiResponseDto> toTestBenchDtoList(List<TestBench> testBenches);
}
