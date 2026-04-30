package roomescape.exception;

import org.springframework.http.HttpStatus;

public enum GlobalErrorCode implements ErrorCode {
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    GlobalErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public int getCode() {
        return httpStatus.value();
    }

    @Override
    public String getMessage() {
        return message;
    }

}
