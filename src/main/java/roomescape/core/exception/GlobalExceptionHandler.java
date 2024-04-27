package roomescape.core.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.core.response.ApiErrorResponse;

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> defaultErrorHandler(Exception e) {
        return ResponseEntity.badRequest().body(new ApiErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ApiErrorResponse> customErrorHandler(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new ApiErrorResponse(e.getMessage()));
    }
}
