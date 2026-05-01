package roomescape.exception;

import org.springframework.http.HttpStatus;

public enum ErrorMessage {
    INVALID_NAME_BLANK(HttpStatus.BAD_REQUEST, "이름은 필수입니다."),
    INVALID_NAME_LENGTH(HttpStatus.BAD_REQUEST, "이름은 20자를 초과할 수 없습니다."),
    INVALID_DATE_NULL(HttpStatus.BAD_REQUEST, "날짜는 필수입니다."),
    INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, "유효하지 않은 날짜입니다."),
    INVALID_TIME_ID_FORMAT(HttpStatus.BAD_REQUEST, "시간 ID는 0보다 커야 합니다."),

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
