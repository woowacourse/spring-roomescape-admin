package roomescape.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    RESERVATION_NOT_FOUNT(BAD_REQUEST, "요청한 예약을 찾을 수 없습니다."),

    RESERVATION_TIME_NOT_FOUND(BAD_REQUEST, "요청한 예약 시간을 찾을 수 없습니다."),
    RESERVATION_TIME_IS_REFERENCED(BAD_REQUEST, "요청한 예약 시간은 다른 데이터에서 참조하고 있습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(final HttpStatus httpStatus, final String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
