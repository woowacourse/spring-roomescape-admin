package roomescape.reservation.exception;

public enum ReservationErrorCode {
    DUPLICATE("해당 날짜의 해당 시간은 이미 예약되었습니다"),
    NOT_FOUND("예약을 찾을 수 없습니다");

    private final String message;

    ReservationErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

