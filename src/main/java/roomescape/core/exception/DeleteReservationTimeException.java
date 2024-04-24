package roomescape.core.exception;

import org.springframework.http.HttpStatus;

public class DeleteReservationTimeException extends CustomException {

    public DeleteReservationTimeException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
