package roomescape.reservation.exception;

import org.springframework.http.HttpStatus;

public enum ReservationErrorCode {
    DUPLICATE(HttpStatus.BAD_REQUEST, "해당 날짜의 해당 시간은 이미 예약되었습니다"),
    INTEGRITY_VIOLATION(HttpStatus.BAD_REQUEST, "요청이 데이터 무결성 조건을 위반했습니다"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "예약을 찾을 수 없습니다");

    private final HttpStatus status;
    private final String message;

    ReservationErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
