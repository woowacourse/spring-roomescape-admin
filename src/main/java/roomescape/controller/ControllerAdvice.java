package roomescape.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.common.exception.ApiException;
import roomescape.controller.dto.response.ErrorResponse;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ErrorResponse> apiException(ApiException e, HttpServletRequest request) {
        e.printStackTrace(); // TODO: 로깅 도입
        return ResponseEntity.status(e.getStatusCode())
                .body(ErrorResponse.of(request.getRequestURI(), e.getMessage()));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> error(Exception e, HttpServletRequest request) {
        e.printStackTrace(); // TODO: 로깅 도입
        return ResponseEntity.status(500)
                .body(ErrorResponse.of(request.getRequestURI(), "알 수 없는 서버 에러"));
    }
}
