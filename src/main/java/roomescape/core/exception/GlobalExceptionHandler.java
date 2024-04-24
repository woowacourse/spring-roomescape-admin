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

    @ExceptionHandler(value = DeleteReservationTimeException.class)
    public ResponseEntity<ApiErrorResponse> deleteReservationTimeErrorHandler(Exception e) {
        return ResponseEntity.badRequest().body(new ApiErrorResponse(e.getMessage()));
    }
}
