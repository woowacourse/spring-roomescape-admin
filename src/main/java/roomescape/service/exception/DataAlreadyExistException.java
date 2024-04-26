package roomescape.service.exception;

public class DataAlreadyExistException extends IllegalStateException {
    public DataAlreadyExistException(String s) {
        super(s);
    }
}
