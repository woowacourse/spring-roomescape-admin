package roomescape.global.exception.customException;

public class ReservationTimeException extends RuntimeException{

    public ReservationTimeException(String message) {
        super(message);
    }

    public ReservationTimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
