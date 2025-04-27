package roomescape.domain.exception;

public class EmptyReservationTimeException extends RuntimeException {

    public EmptyReservationTimeException(String message) {
        super("[ERROR] " + message);
    }
}
