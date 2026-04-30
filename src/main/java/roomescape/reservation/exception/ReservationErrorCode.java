package roomescape.reservation.exception;

import org.springframework.http.HttpStatus;
import roomescape.exception.ErrorCode;

public enum ReservationErrorCode implements ErrorCode {
    RESERVATION_DUPLICATE(HttpStatus.CONFLICT, "예약은 중복 생성이 불가능합니다."),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "찾는 예약이 없습니다.");


    private final HttpStatus httpStatus;

    private final String message;

    ReservationErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public int getCode() {
        return httpStatus.value();
    }

    @Override
    public String getMessage() {
        return message;
    }

}
