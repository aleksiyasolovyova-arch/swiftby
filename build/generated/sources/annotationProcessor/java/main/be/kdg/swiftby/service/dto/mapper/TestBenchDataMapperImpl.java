package be.kdg.swiftby.service.dto.mapper;

import be.kdg.swiftby.domain.report.TestBenchData;
import be.kdg.swiftby.service.dto.TestBenchDataDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-04T15:39:05+0100",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class TestBenchDataMapperImpl implements TestBenchDataMapper {

    @Override
    public TestBenchData toTestBench(TestBenchDataDto testBenchDataDto) {
        if ( testBenchDataDto == null ) {
            return null;
        }

        TestBenchData testBenchData = new TestBenchData();

        testBenchData.setRollerTorque( testBenchDataDto.rollerTorque() );
        testBenchData.setLoadCell( testBenchDataDto.loadCell() );
        testBenchData.setRol( testBenchDataDto.rol() );
        testBenchData.setLoadPower( testBenchDataDto.loadPower() );
        testBenchData.setStatusPlug( testBenchDataDto.statusPlug() );

        return testBenchData;
    }
}
