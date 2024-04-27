package roomescape.exception;

import org.springframework.http.HttpStatus;
import roomescape.core.exception.CustomException;

public class ReservationTimeException extends CustomException {

    public ReservationTimeException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
