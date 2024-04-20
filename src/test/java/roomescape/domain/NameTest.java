package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NameTest {

    @DisplayName("생성 테스트")
    @Test
    void create() {
        assertThatCode(() -> new Name("아톰"))
                .doesNotThrowAnyException();
    }

    @DisplayName("이름은 공백을 제외한 1자 이상이어야 한다.")
    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", "\n", "\t"})
    void checkNameBlank(String source) {
        assertThatThrownBy(() -> new Name(source))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 공백을 제외한 1자 이상이어야 합니다.");
    }
}
