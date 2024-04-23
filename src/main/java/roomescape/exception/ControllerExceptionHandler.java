package roomescape.exception;

import java.time.format.DateTimeParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ExceptionResponse response = new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.badRequest()
                .body(response);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ExceptionResponse> handleDateTimeParseException() {
        ExceptionResponse response = new ExceptionResponse(HttpStatus.BAD_REQUEST, "날짜/시간 포맷이 잘못되었습니다.");
        return ResponseEntity.badRequest()
                .body(response);
    }
}
