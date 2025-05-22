package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.bike.BikeInstance;
import be.kdg.swiftby.domain.bike.BikeOwnership;
import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.domain.testEnv.BikeOwner;
import be.kdg.swiftby.repository.bike.BikeInstanceRepository;
import be.kdg.swiftby.repository.bike.BikeOwnershipRepository;
import be.kdg.swiftby.repository.testEnvironment.BikeOwnerRepository;
import be.kdg.swiftby.service.intf.BikeOwnershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BikeOwnershipServiceImpl implements BikeOwnershipService {

    private final BikeOwnershipRepository bikeOwnershipRepository;
    private final BikeOwnerRepository bikeOwnerRepository;
    private final BikeInstanceRepository bikeInstanceRepository;

    @Override
    public void assignOwnerToBike(Long ownerId, Long bikeInstanceId) {
        BikeOwner owner = bikeOwnerRepository.findById(ownerId)
                .orElseThrow(() -> NotFoundException.forBikeOwner(ownerId));

        BikeInstance bike = bikeInstanceRepository.findById(bikeInstanceId)
                .orElseThrow(() -> NotFoundException.forBike(bikeInstanceId));

        BikeOwnership ownership = new BikeOwnership();
        ownership.setOwner(owner);
        ownership.setBike(bike);

        bikeOwnershipRepository.save(ownership);
    }


    @Override
    public List<BikeInstance> getBikesByOwner(Long ownerId) {
        return bikeOwnershipRepository.findByOwnerId(ownerId)
                .stream().map(BikeOwnership::getBike).toList();
    }

    @Override
    public List<BikeOwner> getOwnersByBike(Long bikeId) {
        return bikeOwnershipRepository.findByBikeId(bikeId)
                .stream().map(BikeOwnership::getOwner).toList();
    }

    @Override
    public void removeOwnership(Long ownershipId) {
        bikeOwnershipRepository.deleteById(ownershipId);
    }

    @Override
    public boolean isOwner(Long ownerId, Long bikeId) {
        return bikeOwnershipRepository.existsByOwnerIdAndBikeId(ownerId, bikeId);
    }
}
