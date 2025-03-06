package be.kdg.swiftby.service.dto.mapper;

import be.kdg.swiftby.domain.bike.BIKE_SIZE;
import be.kdg.swiftby.domain.bike.Bike;
import be.kdg.swiftby.domain.bike.POWERTRAIN;
import be.kdg.swiftby.service.dto.BikeDto;
import be.kdg.swiftby.service.dto.MotorDto;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-06T15:16:05+0100",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class BikeMapperImpl implements BikeMapper {

    @Autowired
    private MotorMapper motorMapper;

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
        bike.setMotor( motorMapper.toMotor( bikeDto.motor() ) );

        return bike;
    }

    @Override
    public BikeDto toBikeDto(Bike bike) {
        if ( bike == null ) {
            return null;
        }

        Long id = null;
        String brand = null;
        String type = null;
        String chassisNumber = null;
        POWERTRAIN powertrain = null;
        BIKE_SIZE bikeSize = null;
        int maxSupport = 0;
        MotorDto motor = null;
        int batteryCapacity = 0;

        id = bike.getId();
        brand = bike.getBrand();
        type = bike.getType();
        chassisNumber = bike.getChassisNumber();
        powertrain = bike.getPowertrain();
        bikeSize = bike.getBikeSize();
        if ( bike.getMaxSupport() != null ) {
            maxSupport = bike.getMaxSupport();
        }
        motor = motorMapper.toMotorDto( bike.getMotor() );
        if ( bike.getBatteryCapacity() != null ) {
            batteryCapacity = bike.getBatteryCapacity();
        }

        BikeDto bikeDto = new BikeDto( id, brand, type, chassisNumber, powertrain, bikeSize, maxSupport, motor, batteryCapacity );

        return bikeDto;
    }
}
