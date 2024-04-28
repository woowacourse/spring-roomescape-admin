package roomescape.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import roomescape.exception.IllegalRequestFormException;
import roomescape.exception.IllegalReservationTimeException;

class GameTimeTest {
    @DisplayName("예약 가능 시각이 null인 경우 예외가 발생한다")
    @Test
    void createWithNullTest() {
        assertThatThrownBy(() -> new GameTime(null))
                .isInstanceOf(IllegalRequestFormException.class);
    }

    @DisplayName("예약 가능 시각이 정각 단위가 아닌 경우 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 58, 59})
    void createWithNonHourlyTimeTest(int nonHourlyMinute) {
        assertThatThrownBy(() -> new GameTime(LocalTime.of(3, nonHourlyMinute)))
                .isInstanceOf(IllegalReservationTimeException.class);
    }
}
