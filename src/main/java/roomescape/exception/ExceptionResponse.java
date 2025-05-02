package roomescape.exception;

import org.springframework.http.HttpStatus;

public record ExceptionResponse(
        HttpStatus error,
        String message
) {

}
