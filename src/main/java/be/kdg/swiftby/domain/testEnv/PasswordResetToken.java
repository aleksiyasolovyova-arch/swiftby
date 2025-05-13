package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expiresAt;


    @OneToOne
    private BikeOwner user;

    private boolean used = false;

    public PasswordResetToken(String token, LocalDateTime expiresAt, BikeOwner user, boolean used) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.user = user;
        this.used = used;
    }

    public PasswordResetToken() {

    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public BikeOwner getUser() {
        return user;
    }

    public void setUser(BikeOwner user) {
        this.user = user;
    }
}
