package roomescape.reservation.exception;

import org.springframework.http.HttpStatus;
import roomescape.exception.ExceptionCode;

public enum ReservationExceptionCode implements ExceptionCode {

    RESERVATION_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "해당 시간에 예약이 존재합니다."),
    ;
    private final HttpStatus httpStatus;
    private final String message;

    ReservationExceptionCode(HttpStatus httpStatus, String message) {
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
