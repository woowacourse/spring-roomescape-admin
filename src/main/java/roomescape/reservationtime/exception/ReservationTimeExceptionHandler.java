package roomescape.reservationtime.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.exception.ErrorResponse;

@RestControllerAdvice(basePackages = "roomescape.reservationtime")
public class ReservationTimeExceptionHandler {

    @ExceptionHandler(DuplicateReservationTimeException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateReservationTime(DuplicateReservationTimeException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(ReservationTimeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReservationTimeNotFound(ReservationTimeNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }
}

