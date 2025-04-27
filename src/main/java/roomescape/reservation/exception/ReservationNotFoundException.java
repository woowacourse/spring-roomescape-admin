package roomescape.reservation.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(Long id) {
        super("해당 ID의 예약이 존재하지 않습니다. id = " + id);
    }

    public ReservationNotFoundException(Long id, Throwable cause) {
        super("해당 ID의 예약이 존재하지 않습니다. id = " + id, cause);
    }
}
