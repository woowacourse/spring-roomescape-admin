package roomescape.exception;

public class InvalidReservationException extends IllegalArgumentException {

    private static final String ERROR_PREFIX = "[ERROR]";

    public InvalidReservationException(String message) {
        super(ERROR_PREFIX + message);
    }
}
