package roomescape.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> validation(RoomEscapeException e) {
        return createBadRequestResponse(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> validation(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .getFirst()
                .getDefaultMessage();

        return createBadRequestResponse(errorMessage);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> validation(HttpMessageNotReadableException e) {
        String errorMessage = resolveErrorMessage(e);

        return createBadRequestResponse(errorMessage);
    }

    private String resolveErrorMessage(HttpMessageNotReadableException e) {
        if (e.getCause() instanceof InvalidFormatException invalidFormatException) {
            String fieldName = invalidFormatException.getPath()
                    .getFirst()
                    .getFieldName();

            if (fieldName.equals("startAt")) {
                return "[ERROR] 시간 형식은 HH:mm 이어야 합니다.";
            }

            if (fieldName.equals("date")) {
                return "[ERROR] 날짜 형식은 yyyy-MM-dd 이어야 합니다.";
            }
        }
        return e.getMessage();
    }

    private ResponseEntity<ErrorResponse> createBadRequestResponse(String errorMessage) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errorMessage));
    }

    public record ErrorResponse(String errorMessage) {
    }
}
