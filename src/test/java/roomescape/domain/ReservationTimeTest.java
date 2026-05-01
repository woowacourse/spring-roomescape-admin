package roomescape.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {

    @Test
    @DisplayName("시작 시간이 빈 값이면 오류가 발생한다")
    void startAt_blank_throw_exception() {
        Assertions.assertThatThrownBy(() -> new ReservationTime(null, ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("startAt은 도메인에서 필수값이며 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("시작 시간이 null이면 오류가 발생한다")
    void startAt_null_throw_exception() {
        Assertions.assertThatThrownBy(() -> new ReservationTime(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("startAt은 도메인에서 필수값이며 공백일 수 없습니다.");
    }
}
