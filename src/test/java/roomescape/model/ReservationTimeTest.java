package roomescape.model;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.exception.UserIllegalArgumentException;

class ReservationTimeTest {

    @Test
    void createTest() {
        // given
        LocalTime time = LocalTime.of(10, 0);

        // when & then
        assertThatCode(() -> new ReservationTime(1L, time))
                .doesNotThrowAnyException();
    }

    @DisplayName("예약 시간이 null인 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCreateWithNullTime() {
        // given
        LocalTime time = null;

        // when & then
        assertThatCode(() -> new ReservationTime(1L, time))
                .isInstanceOf(UserIllegalArgumentException.class)
                .hasMessage("예약 시간이 입력되지 않았습니다.");
    }
}
