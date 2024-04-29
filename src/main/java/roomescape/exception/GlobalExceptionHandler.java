package roomescape.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(IllegalRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequest(IllegalRequestException e) {
        log.info(e.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleInternalServerException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.internalServerError().body(new ExceptionResponse("서버 관리자에게 문의하세요"));
    }
}
