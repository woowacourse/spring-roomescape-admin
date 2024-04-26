package roomescape.admin.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.admin.reservation.service.exception.NoSuchDeleteIdException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<String> handleNoSuchDeleteIdException(NoSuchDeleteIdException e) {
        return ResponseEntity.ok(e.getMessage());
    }
}
