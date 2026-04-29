package roomescape.exception;

import org.springframework.http.HttpStatus;

public interface CustomException {
    HttpStatus getStatus();
    String getMessage();
}
