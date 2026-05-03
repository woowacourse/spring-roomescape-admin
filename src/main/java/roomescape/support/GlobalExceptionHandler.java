package roomescape.support;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RoomescapeException.class)
    public ResponseEntity<ErrorResponse> handleRoomescapeException(RoomescapeException exception) {
        final RoomescapeErrorCode errorCode = exception.getErrorCode();
        return ErrorResponse.of(errorCode);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        final RoomescapeErrorCode errorCode = RoomescapeErrorCode.INTERNAL_SERVER_ERROR;
        return ErrorResponse.of(errorCode);
    }
}
