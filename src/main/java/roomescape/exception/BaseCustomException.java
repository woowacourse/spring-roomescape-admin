package roomescape.exception;

import org.springframework.http.HttpStatus;

public class BaseCustomException extends RuntimeException implements CustomException{
    private final ErrorMessage errorMessage;

    public BaseCustomException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    @Override
    public HttpStatus getStatus() {
        return errorMessage.getHttpStatus();
    }
}
