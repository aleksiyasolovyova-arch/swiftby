package be.kdg.swiftby.presentation.webapi.dto;

import be.kdg.swiftby.domain.testEnv.TestBench;
import be.kdg.swiftby.presentation.webapi.dto.response.TestBenchApiRequestDto;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TestBenchApiMapper {
    TestBench toTestBench(TestBenchApiRequestDto testBenchApiRequestDto);

    TestBenchApiRequestDto toTestBenchDto(TestBench testBench);

    List<TestBenchApiRequestDto> toTestBenchDtoList(List<TestBench> testBenches);
}
