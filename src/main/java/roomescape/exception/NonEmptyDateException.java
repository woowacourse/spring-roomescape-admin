package roomescape.exception;

import org.springframework.http.HttpStatus;
import roomescape.core.exception.CustomException;

public class NonEmptyDateException extends CustomException {

    public NonEmptyDateException() {
        super(HttpStatus.BAD_REQUEST, "예약날짜는 비어있을 수 없습니다");
    }
}
