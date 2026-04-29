package roomescape.exception;

public class ReservationException extends ApplicationException {

    public ReservationException(ErrorCode errorCode) {
        super(errorCode);
    }
}