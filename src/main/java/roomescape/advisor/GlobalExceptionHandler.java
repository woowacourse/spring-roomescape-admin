package roomescape.advisor;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.advisor.handler.FormatErrorHandler;
import roomescape.error.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final List<FormatErrorHandler> formatErrorHandlers;

    public GlobalExceptionHandler(List<FormatErrorHandler> formatErrorHandlers) {
        this.formatErrorHandlers = formatErrorHandlers;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleException(HttpMessageNotReadableException e) {

        Throwable cause = e.getCause();

        if (cause instanceof InvalidFormatException formatException) {
            return formatErrorHandlers.stream()
                    .filter(h -> h.supports(formatException))
                    .findAny()
                    .map(h -> ResponseEntity.badRequest().body(h.handle(formatException)))
                    .orElse(ResponseEntity.badRequest().body(new ErrorResponse("잘못된 형식입니다.")));
        }

        return ResponseEntity.badRequest().build();
    }
}
