package be.kdg.swiftby.domain.exception;

public class UserAlreadyExistsException extends RuntimeException {
    private String email;

    public UserAlreadyExistsException(String email) {
        this.email = email;
    }

    @Override
    public String getMessage() {
        return String.format("User with email: %s already exists.", email);
    }
}
