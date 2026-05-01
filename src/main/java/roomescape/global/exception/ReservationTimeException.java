package roomescape.global.exception;

public class ReservationTimeException extends RuntimeException{

    public ReservationTimeException(String message) {
        super(message);
    }

    public ReservationTimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
