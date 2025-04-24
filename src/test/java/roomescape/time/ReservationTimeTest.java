package roomescape.time;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {
    @DisplayName("시간이_null이면_예외를_발생한다")
    @Test
    void should_ThrowException_WhenTimeIsNull() {
        // when
        // then
        assertThatThrownBy(() -> new ReservationTime(1L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시간 정보는 null일 수 없습니다.");
    }
}
