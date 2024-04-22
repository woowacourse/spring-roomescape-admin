package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class ReservationTimeTest {
    @Test
    @DisplayName("문자열을 통해 예약시간을 생성한다.")
    void some() {
        final String value = "10:00";
        assertThatCode(() -> ReservationTime.from(value))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"13,01", "저녁 10:00", "25:01", "23:61"})
    @DisplayName("정해진 양식의 문자열(10:00,23:00) 이 아니면, 예외를 발생한다.")
    void throw_exception_when_not_valid_format(final String invalidValue) {
        assertThatThrownBy(() -> ReservationTime.from(invalidValue))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
