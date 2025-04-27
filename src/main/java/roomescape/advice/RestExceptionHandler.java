package roomescape.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.exception.BadRequestException;
import roomescape.exception.ConflictException;
import roomescape.exception.NotFoundException;

@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFound(NotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> badRequest(BadRequestException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> conflict(ConflictException e) {
        return ResponseEntity.status(409).body(e.getMessage());
    }
}
