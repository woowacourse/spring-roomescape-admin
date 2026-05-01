package roomescape.time.exception;

import org.springframework.http.HttpStatus;
import roomescape.exception.errorCode.ErrorCode;

public enum ReservationTimeErrorCode implements ErrorCode {
    RESERVATION_TIME_DUPLICATE(HttpStatus.CONFLICT, "예약 시간은 중복 생성이 불가능합니다."),
    RESERVATION_TIME_NOT_FOUND(HttpStatus.NOT_FOUND, "찾는 예약 시간이 없습니다.");


    private final HttpStatus httpStatus;

    private final String message;

    ReservationTimeErrorCode(HttpStatus httpStatus, String message) {
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
