package roomescape.core.exception;

import org.springframework.http.HttpStatus;

public class ReservationException extends CustomException {

    public ReservationException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
