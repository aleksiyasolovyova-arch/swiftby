package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.exception.AlreadyExistsException;
import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.domain.testEnv.BikeOwner;
import be.kdg.swiftby.domain.testEnv.PasswordResetToken;
import be.kdg.swiftby.repository.testEnvironment.BikeOwnerRepository;
import be.kdg.swiftby.repository.testEnvironment.PasswordResetTokenRepository;
import be.kdg.swiftby.service.intf.BikeOwnerService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BikeOwnerServiceImpl implements BikeOwnerService {
    BikeOwnerRepository bikeOwnerRepository;
    UserUtilities userUtilities;
    PasswordResetTokenRepository passwordResetTokenRepository;

    Logger log = LoggerFactory.getLogger(BikeOwnerServiceImpl.class);

    public BikeOwnerServiceImpl(BikeOwnerRepository bikeOwnerRepository,
                                UserUtilities userUtilities,
                                PasswordResetTokenRepository passwordResetTokenRepository) {
        this.bikeOwnerRepository = bikeOwnerRepository;
        this.userUtilities = userUtilities;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
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

//    @Override
//    public BikeOwner save(String email, String password, String firstName, String lastName, String phoneNumber) {
//        if (userUtilities.isExistingUser(email)) {
//            throw AlreadyExistsException.forUserWithEmail(email);
//        }
//
//        return bikeOwnerRepository.save(new BikeOwner(email, password, firstName, lastName, phoneNumber));
//    }

    @Override
    public BikeOwner save(String email, String firstName, String lastName, String phoneNumber) {
        if (userUtilities.isExistingUser(email)) {
        throw AlreadyExistsException.forUserWithEmail(email);
    }
        BikeOwner newUser = bikeOwnerRepository.save(new BikeOwner(email, firstName, lastName, phoneNumber));
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(
                token,
                LocalDateTime.now().plusDays(1),
                newUser,
                false
        );
        passwordResetTokenRepository.save(resetToken);
        return newUser;
    }
    @Override
    public void remove(Long id) {
        if (!bikeOwnerRepository.existsById(id)) {
            throw NotFoundException.forBikeOwner(id);
        }

        bikeOwnerRepository.deleteById(id);
        log.debug("Removed BikeOwner with id {}", id);
    }
    @Transactional
    public List<BikeOwner> searchOwnersByEmail(String email) {
        return bikeOwnerRepository.findByEmailContainingIgnoreCase(email);
    }
}
