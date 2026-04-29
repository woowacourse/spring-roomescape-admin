package roomescape.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

    @ParameterizedTest
    @ValueSource(strings = {"24:00", "1:00", "12:60", "abc", "12:0"})
    @DisplayName("시간 형식이 올바르지 않으면 오류가 발생한다")
    void startAt_invalid_format_throw_exception(String invalidTime) {
        Assertions.assertThatThrownBy(() -> new ReservationTime(null, invalidTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시간 형식이 올바르지 않습니다 : " + invalidTime);
    }

    @ParameterizedTest
    @ValueSource(strings = {"00:00", "12:00", "23:59"})
    @DisplayName("올바른 시간 형식이면 성공적으로 생성된다")
    void startAt_valid_format_success(String validTime) {
        Assertions.assertThatCode(() -> new ReservationTime(null, validTime))
                .doesNotThrowAnyException();
    }
}
