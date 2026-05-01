package roomescape.exception;


import lombok.Getter;
import roomescape.exception.errorCode.ErrorCode;

@Getter
public class RoomescapeException extends RuntimeException {

    private final ErrorCode errorCode;

    public RoomescapeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
