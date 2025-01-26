package roomescape.exception;

public class ForbiddenException extends BadRequestException {

    public ForbiddenException(ErrorCode code) {
        super(code);
    }

    public ForbiddenException(ErrorCode code, Throwable cause) {
        super(code, cause);
    }
}
