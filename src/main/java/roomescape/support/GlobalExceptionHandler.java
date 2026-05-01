package roomescape.support;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleRoomescapeException(RoomescapeException exception) {
        final RoomescapeErrorCode errorCode = exception.getErrorCode();
        return ErrorResponse.of(errorCode);
    }
}
