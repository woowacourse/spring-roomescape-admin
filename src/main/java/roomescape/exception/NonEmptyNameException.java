package roomescape.exception;

import org.springframework.http.HttpStatus;
import roomescape.core.exception.CustomException;

public class NonEmptyNameException extends CustomException {

    public NonEmptyNameException() {
        super(HttpStatus.BAD_REQUEST, "이름은 비어있을 수 없습니다");
    }
}
