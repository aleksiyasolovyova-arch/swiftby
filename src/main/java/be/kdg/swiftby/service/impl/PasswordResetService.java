package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.testEnv.BikeOwner;
import be.kdg.swiftby.domain.testEnv.PasswordResetToken;
import be.kdg.swiftby.repository.testEnvironment.BikeOwnerRepository;
import be.kdg.swiftby.repository.testEnvironment.PasswordResetTokenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {
    private final PasswordResetTokenRepository tokenRepository;
    private final BikeOwnerRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BikeOwnerRepository bikeOwnerRepository;

    public PasswordResetService(PasswordResetTokenRepository tokenRepository, BikeOwnerRepository userRepository, PasswordEncoder passwordEncoder, BikeOwnerRepository bikeOwnerRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.bikeOwnerRepository = bikeOwnerRepository;
    }

    public void createResetTokenFor(BikeOwner user) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, LocalDateTime.now().plusDays(1), user, false);
        tokenRepository.save(resetToken);

        // TODO: SEND EMAIL WITH THE LINK FROM BELOW
        System.out.println("Password reset link: http://localhost:8080/set-password?token=" + token);
    }

    public void setPassword(String token, String rawPassword) {
        PasswordResetToken tokenEntity = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (tokenEntity.isUsed() || tokenEntity.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expired or already used");
        }
        BikeOwner user = tokenEntity.getUser();
        user.setPassword(passwordEncoder.encode(rawPassword));
        bikeOwnerRepository.save(user);
        tokenEntity.setUsed(true);
        tokenRepository.save(tokenEntity);
    }

}
