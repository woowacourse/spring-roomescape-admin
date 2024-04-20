package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.exception.InvalidReservationException;

@ControllerAdvice
public class ReservationExceptionHandler {
    @ExceptionHandler
    protected ResponseEntity<String> handleInvalidReservationException(InvalidReservationException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
