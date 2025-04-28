package roomescape.reservation.exception;

public class ReservationTimeNotFoundException extends RuntimeException {

    private static final String MESSAGE = "해당 ID의 예약 시간이 존재하지 않습니다. id = ";

    public ReservationTimeNotFoundException(Long id) {
        super(MESSAGE + id);
    }

    public ReservationTimeNotFoundException(Long id, Throwable e) {
        super(MESSAGE + id);
    }
}
