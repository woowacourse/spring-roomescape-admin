package roomescape.exception;


import lombok.Getter;

@Getter
public class RoomescapeException extends RuntimeException {

    private final ErrorCode errorCode;

    public RoomescapeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
