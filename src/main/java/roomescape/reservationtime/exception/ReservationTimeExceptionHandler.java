package roomescape.reservationtime.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.exception.ErrorResponse;

@RestControllerAdvice(basePackages = "roomescape.reservationtime")
public class ReservationTimeExceptionHandler {

    @ExceptionHandler(ReservationTimeException.class)
    public ResponseEntity<ErrorResponse> handleReservationTimeException(ReservationTimeException e) {
        System.err.println("[ERROR] " + e.getMessage());

        ErrorResponse response = new ErrorResponse(e.getUserMessage());

        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(response);
    }
}

