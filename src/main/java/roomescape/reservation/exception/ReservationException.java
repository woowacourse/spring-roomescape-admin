package roomescape.reservation.exception;

import roomescape.exception.errorCode.ErrorCode;
import roomescape.exception.RoomescapeException;

public class ReservationException extends RoomescapeException {
    public ReservationException(ErrorCode errorCode) {
        super(errorCode);
    }

}
