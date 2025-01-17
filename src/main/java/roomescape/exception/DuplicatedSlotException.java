package roomescape.exception;

public class DuplicatedSlotException extends BadRequestException {

    public DuplicatedSlotException() {
    }

    public DuplicatedSlotException(String message) {
        super(message);
    }

    public DuplicatedSlotException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedSlotException(Throwable cause) {
        super(cause);
    }

    public DuplicatedSlotException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
