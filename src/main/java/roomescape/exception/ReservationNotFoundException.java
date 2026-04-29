package roomescape.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException() {
        super("예약을 찾을 수 없습니다.");
    }
}
