package roomescape.reservation.exception;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(long id) {
        super(String.format("Reservation not found: %d", id));
    }

}

