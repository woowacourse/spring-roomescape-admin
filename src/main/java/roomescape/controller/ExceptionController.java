package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.domain.exception.EmptyReservationDateException;
import roomescape.domain.exception.EmptyReservationTimeException;
import roomescape.domain.exception.EmptyReserverNameException;
import roomescape.domain.exception.PastReservationException;
import roomescape.service.exception.ReservationNotFoundException;
import roomescape.service.exception.ReservationTimeNotFoundException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @ExceptionHandler({ReservationNotFoundException.class, ReservationTimeNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(PastReservationException.class)
    public ResponseEntity<String> handlePastReservationException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler({EmptyReservationDateException.class, EmptyReservationTimeException.class,
            EmptyReserverNameException.class})
    public ResponseEntity<String> handleEmptyRequestException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
