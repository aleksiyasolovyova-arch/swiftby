package be.kdg.swiftby.domain.exception;


public class AlreadyExistsException extends RuntimeException {
    private AlreadyExistsException(String message) {
        super(message);
    }

    public static AlreadyExistsException forUserWithEmail(String email) {
        return new AlreadyExistsException(String.format("User with email %s already exists!", email));
    }

    public static AlreadyExistsException forBikeWithChassisNumber(String chassisNumber) {
        return new AlreadyExistsException(String.format("Bike with chassis number %s already exists!", chassisNumber));
    }
}
