package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReservationTimeTest {

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L})
    @DisplayName("시간 ID가 0 이하이면 예외를 발생한다.")
    void throwException_When_IdIsNotPositive(Long id) {
        assertThatThrownBy(() -> new ReservationTime(id, LocalTime.of(10, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("시간이 null인 경우 예외를 발생한다.")
    void throwException_When_TimeIsNull() {
        assertThatThrownBy(() -> new ReservationTime(1L, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("LocalTime으로 시간을 생성한다.")
    void makeTime_When_LocalTime() {
        assertThatCode(() -> new ReservationTime(1L, LocalTime.of(12, 30)))
                .doesNotThrowAnyException();
    }
}
