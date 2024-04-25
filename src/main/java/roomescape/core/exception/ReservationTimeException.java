package roomescape.core.exception;

import org.springframework.http.HttpStatus;

public class ReservationTimeException extends CustomException {
    
    public ReservationTimeException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
