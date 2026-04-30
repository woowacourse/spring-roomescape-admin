package roomescape.reservation.exception;

import roomescape.exception.BaseException;

public class ReservationException extends BaseException {
    public ReservationException(int code, String message) {
        super(code, message);
    }

}
