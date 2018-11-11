package diaballik.model.exceptions;

public class InvalidCoordinateException extends RuntimeException {
    public InvalidCoordinateException() {
    }

    public InvalidCoordinateException(final String message) {
        super(message);
    }
}
