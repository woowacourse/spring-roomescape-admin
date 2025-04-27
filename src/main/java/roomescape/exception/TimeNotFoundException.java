package roomescape.exception;

public class TimeNotFoundException extends IllegalArgumentException {

    private static final String DEFAULT_MESSAGE = "해당하는 방탈출 시간 id를 찾을 수 없습니다. id : ";

    public TimeNotFoundException(final String message) {
        super(message);
    }

    public TimeNotFoundException(final Long id) {
        super(DEFAULT_MESSAGE + id);
    }
}
