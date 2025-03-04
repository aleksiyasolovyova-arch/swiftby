package be.kdg.swiftby.service.dto.mapper;

import be.kdg.swiftby.domain.report.BatteryData;
import be.kdg.swiftby.service.dto.BatteryDataDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-04T15:23:00+0100",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class BatteryDataMapperImpl implements BatteryDataMapper {

    @Override
    public BatteryData toBatteryData(BatteryDataDto batteryDataDto) {
        if ( batteryDataDto == null ) {
            return null;
        }

        BatteryData batteryData = new BatteryData();

        batteryData.setChargeStatus( batteryDataDto.chargeStatus() );
        batteryData.setCurrent( batteryDataDto.current() );
        batteryData.setVoltage( batteryDataDto.voltage() );
        batteryData.setCapacity( batteryDataDto.capacity() );
        batteryData.setTemperature( batteryDataDto.temperature() );

        return batteryData;
    }
}
