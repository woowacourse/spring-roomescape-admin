package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionAdvice {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Void> badRequest() {
        return ResponseEntity.badRequest().build();
    }
}
