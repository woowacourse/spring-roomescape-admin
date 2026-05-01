package roomescape.reservationtime.exception;

import org.springframework.http.HttpStatus;

public enum ReservationTimeErrorCode {
    DUPLICATE(HttpStatus.BAD_REQUEST, "이미 존재하는 예약 시간입니다"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "예약 시간을 찾을 수 없습니다"),
    HAS_RESERVATION(HttpStatus.CONFLICT, "예약이 있어 삭제할 수 없습니다");

    private final HttpStatus status;
    private final String message;

    ReservationTimeErrorCode(HttpStatus status, String message) {
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
