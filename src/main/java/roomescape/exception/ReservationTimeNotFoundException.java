package roomescape.exception;

public class ReservationTimeNotFoundException extends RuntimeException {

    public ReservationTimeNotFoundException() {
        super("예약 시간을 찾을 수 없습니다.");
    }
}
