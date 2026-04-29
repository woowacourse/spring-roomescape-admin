package roomescape.exception;

public class PersistenceException extends ApplicationException {

    public PersistenceException(ErrorCode errorCode) {
        super(errorCode);
    }
}
