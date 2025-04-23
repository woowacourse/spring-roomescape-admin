package roomescape.service.exception;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(String message) {
        super("[ERROR] " + message);
    }
}
