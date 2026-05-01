package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {

    @Test
    @DisplayName("시작 시간이 비어있으면 예외가 발생한다.")
    void validateStartAt() {
        assertThatThrownBy(() -> new ReservationTime(1L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작 시간은 필수입니다.");
    }

    @Test
    @DisplayName("정상적인 값으로 예약 시간 객체가 생성된다.")
    void create_Success() {
        assertThatCode(() -> new ReservationTime(1L, LocalTime.of(10, 0)))
                .doesNotThrowAnyException();
    }
}
