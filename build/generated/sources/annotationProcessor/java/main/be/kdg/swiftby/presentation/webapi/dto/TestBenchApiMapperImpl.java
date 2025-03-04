package be.kdg.swiftby.presentation.webapi.dto;

import be.kdg.swiftby.domain.testEnv.TestBench;
import be.kdg.swiftby.presentation.webapi.dto.response.TestBenchApiRequestDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-04T15:23:01+0100",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class TestBenchApiMapperImpl implements TestBenchApiMapper {

    @Override
    public TestBench toTestBench(TestBenchApiRequestDto testBenchApiRequestDto) {
        if ( testBenchApiRequestDto == null ) {
            return null;
        }

        TestBench testBench = new TestBench();

        testBench.setId( testBenchApiRequestDto.id() );
        testBench.setIsActive( testBenchApiRequestDto.isActive() );

        return testBench;
    }

    @Override
    public TestBenchApiRequestDto toTestBenchDto(TestBench testBench) {
        if ( testBench == null ) {
            return null;
        }

        Long id = null;
        Boolean isActive = null;

        id = testBench.getId();
        isActive = testBench.getIsActive();

        TestBenchApiRequestDto testBenchApiRequestDto = new TestBenchApiRequestDto( id, isActive );

        return testBenchApiRequestDto;
    }

    @Override
    public List<TestBenchApiRequestDto> toTestBenchDtoList(List<TestBench> testBenches) {
        if ( testBenches == null ) {
            return null;
        }

        List<TestBenchApiRequestDto> list = new ArrayList<TestBenchApiRequestDto>( testBenches.size() );
        for ( TestBench testBench : testBenches ) {
            list.add( toTestBenchDto( testBench ) );
        }

        return list;
    }
}
