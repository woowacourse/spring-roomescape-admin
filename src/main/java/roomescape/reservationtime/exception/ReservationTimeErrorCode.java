package roomescape.reservationtime.exception;

public enum ReservationTimeErrorCode {
    DUPLICATE("이미 존재하는 예약 시간입니다"),
    NOT_FOUND("예약 시간을 찾을 수 없습니다");

    private final String message;

    ReservationTimeErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

