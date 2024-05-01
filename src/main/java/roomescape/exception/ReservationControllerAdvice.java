package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.controller.ReservationController;
import roomescape.controller.ReservationTimeController;

@RestControllerAdvice(assignableTypes = {ReservationController.class, ReservationTimeController.class})
public class ReservationControllerAdvice {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> invokeIllegalArgument() {
        return ResponseEntity.notFound()
                .build();
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> invokeIllegalStatement(final IllegalStateException e) {
        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> invokeUnDefineException() {
        return ResponseEntity.badRequest()
                .build();
    }
}
