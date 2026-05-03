package roomescape.reservationtime;

public class ReservationTimeNotFoundException extends IllegalArgumentException {
    private final String message;

    public ReservationTimeNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
