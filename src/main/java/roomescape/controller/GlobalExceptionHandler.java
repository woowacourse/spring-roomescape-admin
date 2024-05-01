package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.service.DeleteException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = DeleteException.class)
    public ResponseEntity<String> handleDeleteException(DeleteException deleteException) {
        return new ResponseEntity<>(deleteException.getMessage(), HttpStatus.OK);
    }
}
