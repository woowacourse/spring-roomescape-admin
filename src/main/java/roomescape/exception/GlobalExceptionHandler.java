package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handlerIllegalArgument(IllegalArgumentException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("400", e.getMessage()));
    }
}
