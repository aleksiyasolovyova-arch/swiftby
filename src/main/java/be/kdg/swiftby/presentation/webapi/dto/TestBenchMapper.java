package be.kdg.swiftby.presentation.webapi.dto;

import be.kdg.swiftby.domain.testEnv.TestBench;
import be.kdg.swiftby.presentation.webapi.dto.request.TestBenchDto;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TestBenchMapper {
    TestBench toTestBench(TestBenchDto testBenchDto);

    TestBenchDto toTestBenchDto(TestBench testBench);

    List<TestBenchDto> toTestBenchDtoList(List<TestBench> testBenches);
}
