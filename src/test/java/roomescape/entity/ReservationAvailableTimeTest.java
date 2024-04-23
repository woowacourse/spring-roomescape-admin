package roomescape.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReservationAvailableTimeTest {
    @DisplayName("예약 가능 시각이 null인 경우 예외가 발생한다")
    @Test
    void createWithNullTest() {
        assertThatThrownBy(() -> new ReservationAvailableTime(null))
                .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("예약 가능 시각이 정각 단위가 아닌 경우 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 59})
    void createWithNonHourlyTime(int miniute) {
        assertThatThrownBy(() -> new ReservationAvailableTime(LocalTime.of(1, miniute)))
                .isInstanceOf(IllegalStateException.class);
    }
}
