package roomescape.admin.reservation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.admin.reservation.service.exception.NoSuchDeleteIdException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleNoSuchDeleteIdException(NoSuchDeleteIdException e) {
        return ResponseEntity.ok(new ExceptionResponse(HttpStatus.OK.value(), e.getMessage()));
    }

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(Exception e) {
        return ResponseEntity.internalServerError()
                .body(new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
}
