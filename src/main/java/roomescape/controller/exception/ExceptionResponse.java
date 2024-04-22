package roomescape.controller.exception;

import org.springframework.http.HttpStatus;

public class ExceptionResponse {
    private final int code;
    private final String errorType;
    private final String message;

    public ExceptionResponse(HttpStatus httpStatus, String message) {
        this.code = httpStatus.value();
        this.errorType = httpStatus.getReasonPhrase();
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getErrorType() {
        return errorType;
    }

    public String getMessage() {
        return message;
    }
}
