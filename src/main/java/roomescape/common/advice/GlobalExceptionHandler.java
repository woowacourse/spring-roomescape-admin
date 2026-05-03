package roomescape.common.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "서버 내부에서 예상치 못한 문제가 발생했습니다.";

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnhandledException(Exception e) {
        return ResponseEntity.internalServerError().body(INTERNAL_SERVER_ERROR_MESSAGE);
    }
}
