package roomescape.reservation.exception;

public class DuplicateReservationException extends RuntimeException {

    public DuplicateReservationException() {
        super("Duplicate reservation for the same date and time");
    }

}

