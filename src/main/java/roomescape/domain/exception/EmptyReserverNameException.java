package roomescape.domain.exception;

public class EmptyReserverNameException extends RuntimeException {

    public EmptyReserverNameException(String message) {
        super("[ERROR] " + message);
    }
}
