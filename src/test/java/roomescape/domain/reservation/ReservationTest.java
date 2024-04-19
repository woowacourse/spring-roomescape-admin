package roomescape.domain.reservation;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class ReservationTest {
    @Test
    void 예약날짜가_현재보다_이전이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Reservation("prin", LocalDateTime.now().minusHours(1)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("현재 시간 이후로 예약해야 합니다.");
    }
}
