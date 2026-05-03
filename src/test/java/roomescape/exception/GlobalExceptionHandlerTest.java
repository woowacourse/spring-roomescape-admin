package roomescape.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class GlobalExceptionHandlerTest {
    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("IllegalArgumentException이 발생하면 400 응답을 반환한다.")
    void return400_When_IllegalArgumentExceptionOccurred() {
        IllegalArgumentException exception = new IllegalArgumentException("테스트용 에러 메시지");

        ResponseEntity<String> response = handler.handle(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("테스트용 에러 메시지");
    }

    @Test
    @DisplayName("ReservationNotFoundException이 발생하면 404 응답을 반환한다.")
    void return404_When_ReservationNotFoundExceptionOccurred() {
        ReservationNotFoundException exception = new ReservationNotFoundException("테스트용 에러 메시지");

        ResponseEntity<String> response = handler.handle(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("테스트용 에러 메시지");
    }

    @Test
    @DisplayName("ReservationTimeNotFoundException이 발생하면 404 응답을 반환한다")
    void return404_When_ReservationTimeNotFoundExceptionOccurred() {
        ReservationTimeNotFoundException exception = new ReservationTimeNotFoundException("테스트용 에러 메시지");

        ResponseEntity<String> response = handler.handle(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("ReservationTimeInUseException이 발생하면 400 응답을 반환한다")
    void return400_When_ReservationTimeInUseExceptionOccurred() {
        ReservationTimeInUseException exception = new ReservationTimeInUseException("테스트용 에러 메시지");

        ResponseEntity<String> response = handler.handle(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
