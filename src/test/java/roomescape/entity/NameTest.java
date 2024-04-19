package roomescape.entity;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NameTest {
    @DisplayName("예약자 이름이 null인 경우 생성 시 예외가 발생한다")
    @Test
    void nullNameCreationTest() {
        assertThatThrownBy(() -> new Name(null))
                .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("예약자 이름의 길이가 범위에 맞지 않는 경우 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"", "123456"})
    void invalidLengthNameCreationTest(String name) {
        assertThatThrownBy(() -> new Name(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("조건에 맞는 이름을 생성할 경우 예외가 발생하지 않는다")
    @ParameterizedTest
    @ValueSource(strings = {"1", "12", "123", "1234", "12345"})
    void validNameCreationTest(String name) {
        assertThatCode(() -> new Name(name))
                .doesNotThrowAnyException();
    }
}
