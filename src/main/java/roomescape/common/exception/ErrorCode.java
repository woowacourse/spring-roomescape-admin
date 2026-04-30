package roomescape.common.exception;

import org.springframework.http.HttpStatus;

public record ErrorCode(
        int status,
        HttpStatus error,
        String message
) {

    public static ErrorCode of(HttpStatus error, String meesage) {
        return new ErrorCode(error.value(), error, meesage);
    }

}
