package roomescape.exception;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.exception.errorCode.GlobalErrorCode;
import roomescape.exception.response.ErrorResponse;
import roomescape.exception.response.ValidationError;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException e
    ) {
        List<ValidationError> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> ValidationError.of(error.getField(), error.getCode()))
                .toList();

        return ResponseEntity
                .status(GlobalErrorCode.INVALID_INPUT.getHttpStatus())
                .body(ErrorResponse.of(GlobalErrorCode.INVALID_INPUT, errors));
    }

    @ExceptionHandler(RoomescapeException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            RoomescapeException e
    ) {
        log.warn("BusinessException 발생: {}", e.getMessage(), e);

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(ErrorResponse.of(e.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception e
    ) {
        log.error("Unexpected Exception 발생", e);

        return ResponseEntity
                .status(GlobalErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(ErrorResponse.of(GlobalErrorCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(
            DataIntegrityViolationException e
    ) {
        log.warn("DataIntegrityViolationException 발생", e);

        return ResponseEntity
                .status(GlobalErrorCode.BAD_REQUEST.getHttpStatus())
                .body(ErrorResponse.of(GlobalErrorCode.BAD_REQUEST));
    }

}
