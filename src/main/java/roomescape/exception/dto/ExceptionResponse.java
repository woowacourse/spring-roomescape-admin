package roomescape.exception.dto;

import java.util.List;

public record ExceptionResponse(
    List<String> message
) {

    public ExceptionResponse(String message) {
        this(List.of(message));
    }
}
