package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {

    @Test
    @DisplayName("시작 시각이 null 이면 예외가 발생한다")
    void create_throwsWhenStartAtIsNull() {
        assertThatThrownBy(() -> new ReservationTime(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시간은 필수입니다.");
    }
}
