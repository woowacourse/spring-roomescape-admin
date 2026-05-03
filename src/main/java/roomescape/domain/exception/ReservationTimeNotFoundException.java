package roomescape.domain.exception;

import roomescape.common.exception.ApiException;
import roomescape.common.exception.ErrorCode;

public class ReservationTimeNotFoundException extends ApiException {

    private static final String ERROR_MESSAGE = "존재하지 않는 예약 시간입니다. 요청한 예약 시간 ID: %d";

    public ReservationTimeNotFoundException(Long reservationTimeId) {

        super(ErrorCode.RESERVATION_TIME_NOT_FOUND, String.format(ERROR_MESSAGE, reservationTimeId));
    }
}
