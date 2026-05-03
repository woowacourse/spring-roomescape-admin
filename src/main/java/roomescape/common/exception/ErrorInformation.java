package roomescape.common.exception;

import org.springframework.http.HttpStatus;

public record ErrorInformation(
        int status,
        HttpStatus error,
        String message
) {

    public static ErrorInformation of(HttpStatus error, String message) {
        return new ErrorInformation(error.value(), error, message);
    }

}
