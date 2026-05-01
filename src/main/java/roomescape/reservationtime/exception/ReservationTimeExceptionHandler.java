package roomescape.reservationtime.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.exception.ErrorResponse;

@RestControllerAdvice(basePackages = "roomescape.reservationtime")
public class ReservationTimeExceptionHandler {

    @ExceptionHandler(ReservationTimeException.class)
    public ResponseEntity<ErrorResponse> handleReservationTimeException(ReservationTimeException e) {
        System.err.println("[ERROR] " + e.getMessage());

        HttpStatus status = mapToHttpStatus(e.getErrorCode());
        ErrorResponse response = new ErrorResponse(e.getUserMessage());

        return ResponseEntity
                .status(status)
                .body(response);
    }

    private HttpStatus mapToHttpStatus(ReservationTimeErrorCode errorCode) {
        return switch (errorCode) {
            case DUPLICATE -> HttpStatus.BAD_REQUEST;
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
        };
    }
}

