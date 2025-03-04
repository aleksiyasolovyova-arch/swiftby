package be.kdg.swiftby.service.dto.mapper;

import be.kdg.swiftby.domain.bike.Bike;
import be.kdg.swiftby.service.dto.BikeDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-04T17:33:30+0100",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class BikeMapperImpl implements BikeMapper {

    @Override
    public Bike toBike(BikeDto bikeDto) {
        if ( bikeDto == null ) {
            return null;
        }

        Bike bike = new Bike();

        bike.setId( bikeDto.id() );
        bike.setBrand( bikeDto.brand() );
        bike.setType( bikeDto.type() );
        bike.setChassisNumber( bikeDto.chassisNumber() );
        bike.setPowertrain( bikeDto.powertrain() );
        bike.setBikeSize( bikeDto.bikeSize() );
        bike.setMaxSupport( bikeDto.maxSupport() );
        bike.setBatteryCapacity( bikeDto.batteryCapacity() );

        return bike;
    }
}
