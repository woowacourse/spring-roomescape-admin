package roomescape.exception;

public class ReservationNotFoundException extends IllegalArgumentException {

    public ReservationNotFoundException(Long id) {
        super("Reservation not found: " + id);
    }
}
