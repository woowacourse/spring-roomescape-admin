package roomescape.exception;

import org.springframework.http.HttpStatus;

public abstract class RoomescapeException extends RuntimeException {
    private final HttpStatus httpStatus;

    public RoomescapeException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
