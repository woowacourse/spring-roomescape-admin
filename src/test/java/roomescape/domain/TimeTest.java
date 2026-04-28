package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TimeTest {
    @Test
    @DisplayName("시간이 주어진 형식이 아닌 경우 예외를 발생한다.")
    void throwException_When_IllegalTimeFormat() {
        assertThatThrownBy(() -> new Time("9:00"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("시간 범위를 벗어나면 예외를 발생한다.")
    void throwException_When_OutOfHourRange() {
        assertThatThrownBy(() -> new Time("25:00"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("분 범위를 벗어나면 예외를 발생한다.")
    void throwException_When_OutOfMinuteRange() {
        assertThatThrownBy(() -> new Time("12:64"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("시간이 null인 경우 예외를 발생한다.")
    void throwException_When_TimeIsNull() {
        assertThatThrownBy(() -> new Time(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("정상적인 시간인 경우 예외가 발생하지 않는다.")
    void makeTime_When_legalTime() {
        assertThatCode(() -> new Time("12:30"))
                .doesNotThrowAnyException();
    }
}
