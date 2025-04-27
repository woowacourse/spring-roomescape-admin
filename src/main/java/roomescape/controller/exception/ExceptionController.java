package roomescape.controller.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.dto.exception.ExceptionResponse;
import roomescape.exception.CustomException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> handleCustomException(CustomException exception) {
        ExceptionResponse response = new ExceptionResponse(exception.getMessage());
        return ResponseEntity.status(exception.getStatus()).body(response);
    }
}
