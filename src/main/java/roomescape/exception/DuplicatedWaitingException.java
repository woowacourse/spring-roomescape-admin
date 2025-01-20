package roomescape.exception;

public class DuplicatedWaitingException extends BadRequestException {

    public DuplicatedWaitingException() {
    }

    public DuplicatedWaitingException(String message) {
        super(message);
    }

    public DuplicatedWaitingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedWaitingException(Throwable cause) {
        super(cause);
    }

    public DuplicatedWaitingException(String message, Throwable cause, boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
