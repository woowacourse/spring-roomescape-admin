package roomescape.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    protected final ErrorCode code;

    public BadRequestException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public BadRequestException(ErrorCode code, Throwable cause) {
        super(code.getMessage(), cause);
        this.code = code;
    }
}
