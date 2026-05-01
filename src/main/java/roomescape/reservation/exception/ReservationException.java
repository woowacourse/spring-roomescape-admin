package roomescape.reservation.exception;

public class ReservationException extends RuntimeException {
    private final ReservationErrorCode errorCode;

    public ReservationException(ReservationErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ReservationErrorCode getErrorCode() {
        return errorCode;
    }

    public String getUserMessage() {
        return errorCode.getMessage();
    }
}

