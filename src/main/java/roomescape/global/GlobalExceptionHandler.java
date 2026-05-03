package roomescape.global;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.exception.ErrorCode;
import roomescape.exception.ReservationException;
import roomescape.exception.ReservationTimeException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<String> handleReservationException(final ReservationException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(errorCode.getMessage());
    }

    @ExceptionHandler(ReservationTimeException.class)
    public ResponseEntity<String> handleReservationTimeException(final ReservationTimeException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(errorCode.getMessage());
    }

}
