package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.controller.dto.CommonErrorResponse;

@RestControllerAdvice
public class RatiExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<CommonErrorResponse> clarifyReasonOfIllegalStateException(
            IllegalStateException illegalStateException) {
        return new ResponseEntity<>(
                new CommonErrorResponse(illegalStateException.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<CommonErrorResponse> clarifyReasonOfIllegalArgumentException(
            IllegalArgumentException illegalArgumentException) {
        return new ResponseEntity<>(
                new CommonErrorResponse(illegalArgumentException.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}
