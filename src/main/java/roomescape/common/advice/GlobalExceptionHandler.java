package roomescape.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.common.exception.ErrorInformation;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorInformation> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorInformation errorInformation = ErrorInformation.of(httpStatus, e.getMessage());
        return ResponseEntity.status(httpStatus)
                .body(errorInformation);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorInformation> handleHttpMessageNotReadable(IllegalArgumentException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorInformation errorInformation = ErrorInformation.of(httpStatus, e.getMessage());
        return ResponseEntity.status(httpStatus)
                .body(errorInformation);
    }

}
