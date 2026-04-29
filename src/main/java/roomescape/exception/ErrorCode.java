package roomescape.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Reservation not found: %d"),
    RESERVATION_TIME_NOT_FOUND(HttpStatus.NOT_FOUND, "Reservation time not found: %d");

    private final HttpStatus status;
    private final String messageTemplate;

    ErrorCode(HttpStatus status, String messageTemplate) {
        this.status = status;
        this.messageTemplate = messageTemplate;
    }

    public HttpStatus status() {
        return status;
    }

    public String formatMessage(Object... args) {
        return String.format(messageTemplate, args);
    }
}
