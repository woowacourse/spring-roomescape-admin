package roomescape.reservation.exception;

public class ReservationTimeNotFoundException extends RuntimeException {

    public ReservationTimeNotFoundException(Long id) {
        super("해당 ID의 예약 시간이 존재하지 않습니다. id = " + id);
    }

    public ReservationTimeNotFoundException(Long id, Throwable e) {
        super("해당 ID의 예약 시간이 존재하지 않습니다. id = " + id);
    }
}
