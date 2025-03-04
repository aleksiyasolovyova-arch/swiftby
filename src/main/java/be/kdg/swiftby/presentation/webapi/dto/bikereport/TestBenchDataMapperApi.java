package be.kdg.swiftby.presentation.webapi.dto.bikereport;

import be.kdg.swiftby.domain.report.TestBenchData;
import be.kdg.swiftby.service.dto.TestBenchDataDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TestBenchDataMapperApi {
    TestBenchDataDto toTestBenchDataDto(TestBenchData testBenchData);
}
