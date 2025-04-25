package roomescape.model.exception;

public class ReservationTimeNotFoundException extends RuntimeException {

    public ReservationTimeNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
