package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {

    @Test
    @DisplayName("유효한 시작 시간으로 예약 시간을 생성한다.")
    void should_create_reservation_time_with_valid_start_at() {
        final ReservationTime time = new ReservationTime(1L, "10:00");

        assertThat(time.getStartAt()).isEqualTo("10:00");
    }

    @Test
    @DisplayName("시작 시간이 null이면 예외가 발생한다.")
    void should_throw_exception_when_start_at_is_null() {
        assertThatThrownBy(() -> new ReservationTime(1L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작 시간은 비어있을 수 없습니다.");
    }

    @Test
    @DisplayName("시작 시간이 빈 문자열이면 예외가 발생한다.")
    void should_throw_exception_when_start_at_is_blank() {
        assertThatThrownBy(() -> new ReservationTime(1L, "  "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작 시간은 비어있을 수 없습니다.");
    }
}
