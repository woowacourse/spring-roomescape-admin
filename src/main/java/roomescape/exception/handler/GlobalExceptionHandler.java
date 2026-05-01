package roomescape.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.exception.CustomException;
import roomescape.exception.DataReferencedException;
import roomescape.exception.NotFoundResourceException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({NotFoundResourceException.class, DataReferencedException.class})
    public ResponseEntity<String> handleCustomException(CustomException customException) {
        return new ResponseEntity<>(customException.getMessage(), customException.getStatus());
    }
}
