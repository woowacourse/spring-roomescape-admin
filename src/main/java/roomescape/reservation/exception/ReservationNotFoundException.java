package roomescape.reservation.exception;

public class ReservationNotFoundException extends RuntimeException {

    private static final String MESSAGE = "해당 ID의 예약이 존재하지 않습니다. id = ";

    public ReservationNotFoundException(Long id) {
        super(MESSAGE + id);
    }

    public ReservationNotFoundException(Long id, Throwable cause) {
        super(MESSAGE + id, cause);
    }
}
