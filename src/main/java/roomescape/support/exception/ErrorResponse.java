package roomescape.support.exception;

import org.springframework.http.ResponseEntity;

public record ErrorResponse(
    String code,
    String message
) {

    public static ResponseEntity<ErrorResponse> of(RoomescapeErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus()).
            body(new ErrorResponse(errorCode.name(), errorCode.getMessage()));
    }

}
