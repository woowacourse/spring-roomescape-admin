package roomescape.exception;

import org.springframework.http.HttpStatus;
import roomescape.core.exception.CustomException;

public class DeleteReservationTimeException extends CustomException {

    public DeleteReservationTimeException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
