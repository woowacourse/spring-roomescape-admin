package roomescape.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(BadRequestException e) {
        log.error("code: {}, message: {}", e.getCode().toString(), e.getMessage(), e);
        return new ErrorResponse(e.getCode().toString(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        log.error("code: {}, message: {}", e.getCode().toString(), e.getMessage(), e);
        return new ErrorResponse(e.getCode().toString(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbiddenException(ForbiddenException e) {
        log.error("code: {}, message: {}", e.getCode().toString(), e.getMessage(), e);
        return new ErrorResponse(e.getCode().toString(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleInternalServerError(Exception e) {
        log.error(e.getMessage(), e);
        return "서버에서 예기치 못한 에러가 발생했습니다. 잠시 후 다시 시도해주세요.";
    }
}
