package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.exception.DomainValidationMessage;

class ReservationTest {

    @DisplayName("과거의 날짜와 시간으로 예약을 생성하는 경우 예외를 발생시킨다")
    @Test
    void validatePastDateTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime past = now.minusNanos(1);

        assertThatThrownBy(() -> new Reservation(1L, "reservation1", past.toLocalDate(), past.toLocalTime()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(DomainValidationMessage.PAST_DATE_TIME.getContent());
    }
}