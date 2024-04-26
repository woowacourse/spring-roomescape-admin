package roomescape.exception.reservation.time;

public class NotExistReservationTimeException extends ReservationTimeException {
    private final String message;

    public NotExistReservationTimeException(final long existReservationTimeId) {
        this.message = String.format("ID(%d)에 해당하는 시간이 존재 하지 않습니다", existReservationTimeId);
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
