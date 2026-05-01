package roomescape.time.exception;

import roomescape.exception.errorCode.ErrorCode;
import roomescape.exception.RoomescapeException;

public class ReservationTimeException extends RoomescapeException {

    public ReservationTimeException(ErrorCode errorCode) {
        super(errorCode);
    }

}
