package roomescape.exception;

public class DuplicatedReservationException extends BadRequestException {

    public DuplicatedReservationException() {
    }

    public DuplicatedReservationException(String message) {
        super(message);
    }

    public DuplicatedReservationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedReservationException(Throwable cause) {
        super(cause);
    }

    public DuplicatedReservationException(String message, Throwable cause, boolean enableSuppression,
                                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
