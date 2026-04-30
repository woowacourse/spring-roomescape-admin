package roomescape.exception.response;

import java.util.List;
import roomescape.exception.ErrorCode;

public record ErrorResponse(
        int code,
        String message,
        List<ValidationError> validationErrors
) {
    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static ErrorResponse of(ErrorCode errorCode, List<ValidationError> errors) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), null);
    }
}