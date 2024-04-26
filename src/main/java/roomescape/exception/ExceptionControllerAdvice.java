package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> NoSuchElementException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> IllegalArgumentException() {
        return ResponseEntity.badRequest()
                .body("[ERROR] 잘못된 접근입니다.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> Exception() {
        return ResponseEntity.internalServerError()
                .body("[ERROR] 알 수 없는 오류가 발생했습니다.");
    }
}
