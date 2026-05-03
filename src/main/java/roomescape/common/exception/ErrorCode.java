package roomescape.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    RESERVATION_TIME_NOT_FOUND(HttpStatus.NOT_FOUND.value());

    private final int statusCode;

    ErrorCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
