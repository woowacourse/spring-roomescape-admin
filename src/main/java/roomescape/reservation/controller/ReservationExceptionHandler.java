package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.reservation.domain.exception.PastReservationException;
import roomescape.reservation.domain.exception.ReserverNameEmptyException;
import roomescape.reservation.service.exception.ReservationNotFoundException;

@RestControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler(ReserverNameEmptyException.class)
    public ResponseEntity<Void> handleReserverNameEmptyException(ReserverNameEmptyException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<Void> handleReservationNotFoundException(ReservationNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(PastReservationException.class)
    public ResponseEntity<Void> handlePastReservationException(PastReservationException e) {
        return ResponseEntity.badRequest().build();
    }

}
