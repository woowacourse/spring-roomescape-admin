package roomescape.exception;

public class ReservationNotFoundException extends IllegalArgumentException {

    private static final String DEFAULT_MESSAGE = "해당하는 예약 id를 찾을 수 없습니다. id : ";

    public ReservationNotFoundException(final String message) {
        super(message);
    }

    public ReservationNotFoundException(final Long id) {
        super(DEFAULT_MESSAGE + id);
    }
}
