package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.controller.ReservationController;

@RestControllerAdvice(assignableTypes = ReservationController.class)
public class ReservationControllerAdvice {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> invokeIllegalArgument() {
        return ResponseEntity.notFound()
                .build();
    }
}
