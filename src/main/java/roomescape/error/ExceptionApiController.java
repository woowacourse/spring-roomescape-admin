package roomescape.error;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionApiController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(final IllegalArgumentException exception) {
        return new ErrorResult("BAD_INPUT", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataAccessException.class)
    public ErrorResult dataAccessExHandler(final DataAccessException exception) {
        System.out.println("데이터 접근 예외 발생 :" + exception.getMessage());
        return new ErrorResult("BAD_INPUT", "잘못된 데이터 요청입니다.");
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(ReferDataDeleteException.class)
    public ErrorResult referDataDeleteExHandler(final ReferDataDeleteException exception) {
        return new ErrorResult("NOT_ACCEPTABLE", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResult requestFormatExHandler(final HttpMessageNotReadableException exception) {
        return new ErrorResult("BAD_INPUT", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public ErrorResult nullExHandler(final NullPointerException exception) {
        return new ErrorResult("NOT_NULL", exception.getMessage());
    }
}
