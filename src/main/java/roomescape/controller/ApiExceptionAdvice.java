package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.controller.exception.IllegalRequestException;

@RestControllerAdvice
public class ApiExceptionAdvice {
    @ExceptionHandler(IllegalRequestException.class)
    public ResponseEntity<IllegalRequestException> badRequest(IllegalRequestException e) {
        return ResponseEntity.badRequest().body(e);
    }
}
