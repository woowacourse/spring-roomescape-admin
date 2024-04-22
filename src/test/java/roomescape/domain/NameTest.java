package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class NameTest {

    @Test
    @DisplayName("동등성을 비교한다.")
    void equalsTest() {
        Name name = new Name("웨지");
        assertThat(name).isEqualTo(new Name("웨지"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"웨지", "aru", "12345678901234567890"})
    @DisplayName("올바른 제한 아래에서 이름이 정상적으로 생성된다.")
    void creationTest(String name) {
        assertDoesNotThrow(() -> new Name(name));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이름이 비어있는 경우, 예외를 발생한다.")
    void blankOrNullNameTest(String name) {
        assertThatThrownBy(() -> new Name(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("이름의 길이가 제한을 초과하는 경우, 예외를 발생한다.")
    void nameLengthExceedingTest() {
        String name = "a".repeat(21);
        assertThatThrownBy(() -> new Name(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름의 길이는 20자를 넘을 수 없습니다.");
    }
}
