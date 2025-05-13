package be.kdg.swiftby.service.dto.mapper;

import be.kdg.swiftby.domain.report.TestBenchData;
import be.kdg.swiftby.domain.testEnv.TestBench;
import be.kdg.swiftby.service.dto.TestBenchDataDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TestBenchDataMapper {
    TestBenchData toTestBench(TestBenchDataDto testBenchDataDto);
}
