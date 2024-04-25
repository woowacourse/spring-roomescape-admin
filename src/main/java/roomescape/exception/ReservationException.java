package roomescape.exception;

import org.springframework.http.HttpStatus;
import roomescape.core.exception.CustomException;

public class ReservationException extends CustomException {

    public ReservationException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
