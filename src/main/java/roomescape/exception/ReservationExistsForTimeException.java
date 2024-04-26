package roomescape.exception;

import org.springframework.http.HttpStatus;
import roomescape.core.exception.CustomException;

public class ReservationExistsForTimeException extends CustomException {

    public ReservationExistsForTimeException(long timeId) {
        super(HttpStatus.BAD_REQUEST, "해당 예약시간에 예약이 등록되어 있습니다 (timeId : " + timeId + ")");
    }
}
