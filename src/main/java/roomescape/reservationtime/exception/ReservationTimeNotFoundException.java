package roomescape.reservationtime.exception;

public class ReservationTimeNotFoundException extends RuntimeException {
    public ReservationTimeNotFoundException(long id) {
        super(String.format("Reservation time not found: %d", id));
    }
}

