package roomescape.reservation.service.exception;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(String message) {
        super(message);
    }
}
