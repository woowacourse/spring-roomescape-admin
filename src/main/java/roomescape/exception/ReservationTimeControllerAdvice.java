package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.controller.ReservationTimeController;

@RestControllerAdvice(assignableTypes = ReservationTimeController.class)
public class ReservationTimeControllerAdvice {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> invokeIllegalStatement(final IllegalStateException e) {
        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }
}
