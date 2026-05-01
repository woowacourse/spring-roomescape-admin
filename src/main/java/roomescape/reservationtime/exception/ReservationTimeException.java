package roomescape.reservationtime.exception;

public class ReservationTimeException extends RuntimeException {
    private final ReservationTimeErrorCode errorCode;

    public ReservationTimeException(ReservationTimeErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ReservationTimeErrorCode getErrorCode() {
        return errorCode;
    }

    public String getUserMessage() {
        return errorCode.getMessage();
    }
}

