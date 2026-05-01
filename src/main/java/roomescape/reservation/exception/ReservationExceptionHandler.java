package roomescape.reservation.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.exception.ErrorResponse;
import roomescape.reservationtime.exception.ReservationTimeException;

@RestControllerAdvice(basePackages = "roomescape.reservation")
public class ReservationExceptionHandler {

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<ErrorResponse> handleReservationException(ReservationException e) {
        System.err.println("[ERROR] " + e.getMessage());

        ErrorResponse response = new ErrorResponse(e.getUserMessage());

        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(response);
    }

    @ExceptionHandler(ReservationTimeException.class)
    public ResponseEntity<ErrorResponse> handleReservationTimeException(ReservationTimeException e) {
        System.err.println("[ERROR] " + e.getMessage());

        ErrorResponse response = new ErrorResponse(e.getUserMessage());

        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(response);
    }
}
