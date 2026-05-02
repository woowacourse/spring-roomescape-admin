package roomescape.exception;

public enum ErrorCode {
    RESERVATION_NOT_FOUND("존재하지 않는 예약입니다."),
    RESERVATION_CREATE_FAILED("예약 생성에 실패했습니다."),
    RESERVATION_TIME_NOT_FOUND("존재하지 않는 예약 시간입니다."),
    RESERVATION_TIME_CREATE_FAILED("예약 시간 생성에 실패했습니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
