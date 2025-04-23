package roomescape.time.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.time.service.exception.ReservationTimeNotFoundException;

@RestControllerAdvice
public class ReservationTimeExceptionHandler {

    @ExceptionHandler(ReservationTimeNotFoundException.class)
    public ResponseEntity<Void> handleReservationTimeNotFoundException(ReservationTimeNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
