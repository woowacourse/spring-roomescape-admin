package roomescape.exception;

public class DuplicatedSlotException extends BadRequestException {

    public DuplicatedSlotException(ErrorCode code) {
        super(code);
    }

    public DuplicatedSlotException(ErrorCode code, Throwable cause) {
        super(code, cause);
    }
}
