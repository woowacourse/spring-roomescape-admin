package roomescape.exception;

public class InvalidReservationRequest extends IllegalArgumentException {

    public InvalidReservationRequest() {
        super("유효하지 않은 예약 요청입니다.");
    }
}
