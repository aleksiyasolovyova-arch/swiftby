package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.bike.BikeInstance;
import be.kdg.swiftby.domain.bike.BikeModel;
import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.repository.bike.BikeInstanceRepository;
import be.kdg.swiftby.repository.bike.BikeOwnershipRepository;
import be.kdg.swiftby.service.intf.BikeInstanceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import be.kdg.swiftby.domain.bike.BikeOwnership;
import   be.kdg.swiftby.repository.bike.BikeModelRepository;
import java.util.List;

@Service
public class BikeInstanceServiceImpl implements BikeInstanceService {
    private final BikeInstanceRepository bikeInstanceRepository;
    private final BikeOwnershipRepository bikeOwnershipRepository;
    private final BikeModelRepository bikeModelRepository;

    public BikeInstanceServiceImpl(BikeInstanceRepository bikeInstanceRepository, BikeOwnershipRepository bikeOwnershipRepository, BikeModelRepository bikeModelRepository) {
        this.bikeInstanceRepository = bikeInstanceRepository;
        this.bikeOwnershipRepository = bikeOwnershipRepository;
        this.bikeModelRepository = bikeModelRepository;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_SUPERADMIN')")
    @Override
    public List<BikeInstance> getAll() {
        return bikeInstanceRepository.findAll();
    }

    @Override
    public BikeInstance getById(Long id) {
        return bikeInstanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BikeInstance not found"));
    }

    @Override
    public BikeInstance getByIdWithOwner(Long id) {
        return bikeInstanceRepository.findByIdWithOwnerships(id)
                .orElseThrow(() -> new RuntimeException("BikeInstance not found"));
    }

    @Override
    public List<BikeInstance> getByBikeOwnerId(Long ownerId) {
        return bikeOwnershipRepository.findByOwnerId(ownerId).stream()
                .map(BikeOwnership::getBike)
                .toList();
    }

    @Override
    public List<BikeInstance> getAllByFacilityId(Long facilityId) {
        return bikeInstanceRepository.findAllByFacilityId(facilityId);
    }

    @Override
    public BikeInstance createInstance(String chassisNumber, Long modelId) {
        if (bikeInstanceRepository.findByChassisNumber(chassisNumber).isPresent()) {
            throw new IllegalArgumentException("Bike with this chassis number already exists.");
        }

        BikeModel model = bikeModelRepository.findById(modelId)
                .orElseThrow(() -> NotFoundException.forBike(modelId));

        BikeInstance instance = new BikeInstance();
        instance.setChassisNumber(chassisNumber);
        instance.setModel(model);

        return bikeInstanceRepository.save(instance);
    }

    @Override
    public void remove(Long id) {
        if (!bikeInstanceRepository.existsById(id)) {
            throw NotFoundException.forBike(id);
        }

        bikeInstanceRepository.deleteById(id);
    }

    @Override
    public BikeInstance getByIdWithModelAndMotor(Long id) {
        return bikeInstanceRepository.findByIdWithModelAndMotor(id).get();
    }


}
