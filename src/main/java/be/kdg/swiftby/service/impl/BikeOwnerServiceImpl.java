package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.exception.AlreadyExistsException;
import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.domain.testEnv.BikeOwner;
import be.kdg.swiftby.repository.testEnvironment.BikeOwnerRepository;
import be.kdg.swiftby.service.intf.BikeOwnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeOwnerServiceImpl implements BikeOwnerService {
    BikeOwnerRepository bikeOwnerRepository;
    UserUtilities userUtilities;

    Logger log = LoggerFactory.getLogger(BikeOwnerServiceImpl.class);

    public BikeOwnerServiceImpl(BikeOwnerRepository bikeOwnerRepository, UserUtilities userUtilities) {
        this.bikeOwnerRepository = bikeOwnerRepository;
        this.userUtilities = userUtilities;
    }

    @Override
    public List<BikeOwner> getAll() {
        return bikeOwnerRepository.findAll();
    }

    @Override
    public BikeOwner getById(Long id) {
        return bikeOwnerRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forBikeOwner(id));
    }

    @Override
    public BikeOwner getByEmail(String email) {
        return bikeOwnerRepository.findByEmail(email)
                .orElseThrow(() -> AlreadyExistsException.forUserWithEmail(email));
    }

    @Override
    public BikeOwner save(String email, String password, String firstName, String lastName, String phoneNumber) {
        if (userUtilities.isExistingUser(email)) {
            throw AlreadyExistsException.forUserWithEmail(email);
        }

        return bikeOwnerRepository.save(new BikeOwner(email, password, firstName, lastName, phoneNumber));
    }

    @Override
    public void remove(Long id) {
        if (!bikeOwnerRepository.existsById(id)) {
            throw NotFoundException.forBikeOwner(id);
        }

        bikeOwnerRepository.deleteById(id);
        log.debug("Removed BikeOwner with id {}", id);
    }
}
