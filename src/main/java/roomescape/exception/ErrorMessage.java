package roomescape.exception;

import org.springframework.http.HttpStatus;

public enum ErrorMessage {
    INVALID_RESERVATION_TIME_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 시간 id입니다."),

    CANNOT_DELETE_RESERVATION_TIME_IN_USE(HttpStatus.CONFLICT, "해당 시간을 참조하는 예약 데이터가 존재하기 때문에 삭제할 수 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    ErrorMessage(HttpStatus httpStatus, String message) {
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
