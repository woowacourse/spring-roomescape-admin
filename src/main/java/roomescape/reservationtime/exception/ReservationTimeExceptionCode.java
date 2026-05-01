package roomescape.reservationtime.exception;

import org.springframework.http.HttpStatus;
import roomescape.exception.ExceptionCode;

public enum ReservationTimeExceptionCode implements ExceptionCode {

    RESERVATION_NOT_EXISTS(HttpStatus.BAD_REQUEST, "존재하지 않는 예약 시간입니다."),
    ;
    private final HttpStatus httpStatus;
    private final String message;

    ReservationTimeExceptionCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
