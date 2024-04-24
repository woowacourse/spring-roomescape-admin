package roomescape.admin.reservation.service.exception;

public class NoSuchDeleteIdException extends RuntimeException {

    public static final String ERROR_MESSAGE = "삭제할 시간이 존재하지 않습니다.";

    public NoSuchDeleteIdException() {
        super(ERROR_MESSAGE);
    }
}
