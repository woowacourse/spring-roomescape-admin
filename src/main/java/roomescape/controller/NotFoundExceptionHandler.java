package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NotFoundExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.internalServerError()
                .body("알수 없는 오류입니다.");
    }
}
