package roomescape.support.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum RoomescapeErrorCode {
    INVALID_RESERVATION_NAME(HttpStatus.BAD_REQUEST, "이름은 비어 있을 수 없습니다."),
    INVALID_RESERVATION_DATE(HttpStatus.BAD_REQUEST, "날짜는 필수입니다."),
    INVALID_RESERVATION_TIME(HttpStatus.BAD_REQUEST, "시간은 필수입니다."),
    INVALID_RESERVATION_TIME_FORMAT(HttpStatus.BAD_REQUEST, "시간은 HH:MM 형식이어야 합니다."),
    RESERVATION_TIME_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 예약 시간대 입니다."),
    RESERVATION_TIME_IN_USE(HttpStatus.CONFLICT, "이미 예약이 존재하는 시간대는 삭제할 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"서버 내부 오류가 발생했습니다"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    RoomescapeErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
