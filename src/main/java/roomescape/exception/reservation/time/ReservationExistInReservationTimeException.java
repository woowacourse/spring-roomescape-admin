package roomescape.exception.reservation.time;

public class ReservationExistInReservationTimeException extends ReservationTimeException {
    private final String message;

    public ReservationExistInReservationTimeException(final long existReservationTimeId) {
        this.message = String.format("ID(%d)에 해당하는 예약이 존재합니다", existReservationTimeId);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
