package roomescape.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class NameTest {
    @DisplayName("이름이 공백일 경우 예외로 처리한다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "  ", "\t", "\n"})
    void validateBlank(String input) {
        Assertions.assertThatThrownBy(() -> new Name(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름에 공백을 입력할 수 없습니다.");
    }

    @DisplayName("이름의 길이가 5를 초과하면 예외로 처리한다.")
    @Test
    void validateLength() {
        Assertions.assertThatThrownBy(() -> new Name("123456"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름의 길이는 5 이하여야 합니다.");
    }

    @DisplayName("이름에 숫자, 영어, 한글, -, _를 제외한 문자가 들어가면 예외로 처리한다.")
    @Test
    void validateCharacter() {
        Assertions.assertThatThrownBy(() -> new Name("브*운"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 알파벳, 한글, 숫자, '_', '-'로만 이루어져야 합니다.");
    }
}
