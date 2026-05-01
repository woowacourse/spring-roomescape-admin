package roomescape.reservationtime.exception;

import roomescape.exception.RoomescapeException;

public class ReservationTimeException extends RoomescapeException {

    public ReservationTimeException(ReservationTimeExceptionCode reservationTimeExceptionCode) {
        super(
                reservationTimeExceptionCode.getMessage(),
                reservationTimeExceptionCode.getHttpStatus()
        );
    }
}
