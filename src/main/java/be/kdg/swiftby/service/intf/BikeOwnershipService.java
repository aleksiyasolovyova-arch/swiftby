package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.bike.BikeInstance;
import be.kdg.swiftby.domain.testEnv.BikeOwner;

import java.util.List;

public interface BikeOwnershipService {
    void assignOwnerToBike(Long ownerId, Long bikeInstanceId);
    List<BikeInstance> getBikesByOwner(Long ownerId);
    List<BikeOwner> getOwnersByBike(Long bikeInstanceId);
    void removeOwnership(Long ownershipId);
    boolean isOwner(Long ownerId, Long bikeInstanceId);
}
