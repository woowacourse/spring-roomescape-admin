package roomescape.reservation.exception;

import roomescape.exception.RoomescapeException;

public class ReservationException extends RoomescapeException {

    public ReservationException(ReservationExceptionCode reservationExceptionCode) {
        super(
                reservationExceptionCode.getMessage(),
                reservationExceptionCode.getHttpStatus()
        );
    }
}
