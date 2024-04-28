package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NameTest {

    @DisplayName("예약자의 이름은 빈칸이거나 공백일 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void nameNotBlankTest(String name) {
        assertThatThrownBy(() -> new Name(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 빈 칸이거나 공백일 수 없습니다.");
    }
}
