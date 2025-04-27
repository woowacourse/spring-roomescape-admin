package roomescape.domain.exception;

public class EmptyReservationDateException extends RuntimeException {

    public EmptyReservationDateException(String message) {
        super("[ERROR] " + message);
    }
}
