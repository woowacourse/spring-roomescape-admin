package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {

    @Test
    @DisplayName("예약 시간 생성 시 start 시간이 없으면 예외가 발생한다")
    void ExceptionStartNull() {
        // given
        Long timeId = 1L;
        LocalTime startAt = null;

        // when & then
        assertThatThrownBy(() -> new ReservationTime(timeId, startAt))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작 시간이 존재해야 합니다.");
    }

}