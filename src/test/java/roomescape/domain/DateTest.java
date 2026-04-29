package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DateTest {

    @Test
    @DisplayName("날짜가 null이면 예외를 발생한다.")
    void throwException_When_DateIsNull() {
        assertThatThrownBy(() -> new Date(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2024=01=01",
            "2024:04:04",
            "2024--0-01"
    })
    @DisplayName("날짜가 정상 형태가 아닌 경우 예외를 발생한다.")
    void throwException_When_DateIllegalFormat(String input) {
        assertThatThrownBy(() -> new Date(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2024-13-01",
            "2024-00-01",
            "2024--1-01",
    })
    @DisplayName("날짜의 월이 정상 범위를 넘어가는 경우 예외를 발생한다.")
    void throwException_When_MonthOutOfRange(String input) {
        assertThatThrownBy(() -> new Date(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2024-01-00",
            "2024-01--1",
            "2024-01-33",
    })
    @DisplayName("날짜의 일이 정상 범위를 넘어가는 경우 예외를 발생한다.")
    void throwException_When_DateOutOfRange(String input) {
        assertThatThrownBy(() -> new Date(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("날짜 형식의 문제가 없는 경우 정상적으로 생성된다.")
    void makeDate_When_LegalFormat() {
        assertThatCode(() -> new Date("2024-01-01"))
                .doesNotThrowAnyException();
    }
}
