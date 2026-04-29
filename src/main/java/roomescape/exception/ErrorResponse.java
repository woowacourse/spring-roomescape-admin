package roomescape.exception;

import java.util.Map;

public record ErrorResponse(
        int code,
        String message,
        Map<String, Object> arguments
) {
    public static ErrorResponse of(int code, String message, Map<String, Object> args) {
        return new ErrorResponse(code, message, args);
    }
}