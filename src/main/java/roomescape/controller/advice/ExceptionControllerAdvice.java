package roomescape.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import roomescape.model.exception.ReservationNotFoundException;
import roomescape.model.exception.ReservationTimeNotFoundException;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionControllerAdvice {

    @ResponseBody
    @ExceptionHandler({ReservationNotFoundException.class, ReservationTimeNotFoundException.class})
    public ResponseEntity<ErrorResult> handleReservationNotFoundException(
            final HttpServletRequest request,
            final Throwable exception
    ) {
        return new ResponseEntity<>(
                new ErrorResult(request.getRequestURL().toString(), exception.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ResponseBody
    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    public ResponseEntity<ErrorResult> handleInvalidRequestException(
            final HttpServletRequest request,
            final Throwable exception
    ) {
        return new ResponseEntity<>(
                new ErrorResult(request.getRequestURL().toString(), exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}
