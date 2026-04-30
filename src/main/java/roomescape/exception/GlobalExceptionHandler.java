package roomescape.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException e
    ) {
        Map<String, Object> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), e.getMessage(), errors));
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(
            BaseException e
    ) {
        log.warn("BaseException 발생: {}", e.getMessage(), e);

        return ResponseEntity
                .status(e.getCode())
                .body(ErrorResponse.of(e.getCode(), e.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception e
    ) {
        log.error("Unexpected Exception 발생", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
    }

}
