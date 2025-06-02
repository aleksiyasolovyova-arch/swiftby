package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.bike.BikeInstance;

import java.util.List;

public interface BikeInstanceService {
    List<BikeInstance> getAll();

    BikeInstance getById(Long id);

    BikeInstance getByIdWithOwner(Long id);

    List<BikeInstance> getByBikeOwnerId(Long ownerId);

    List<BikeInstance> getAllByFacilityId(Long facilityId);
    BikeInstance createInstance(String chassisNumber, Long modelId);
    void remove(Long id);
    BikeInstance getByIdWithModelAndMotor(Long id);
    List<BikeInstance> getByBikeOwnerEmail(String email);

}
