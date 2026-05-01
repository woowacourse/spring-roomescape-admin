package roomescape.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Reservation not found: %d"),
    RESERVATION_TIME_NOT_FOUND(HttpStatus.NOT_FOUND, "Reservation time not found: %d"),
    DUPLICATE_RESERVATION_TIME(HttpStatus.BAD_REQUEST, "이미 존재하는 예약 시간입니다: %s"),
    DUPLICATE_RESERVATION(HttpStatus.BAD_REQUEST, "해당 날짜의 해당 시간은 이미 예약되었습니다");

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
