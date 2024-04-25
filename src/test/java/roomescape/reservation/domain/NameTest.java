package roomescape.reservation.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("이름 테스트")
class NameTest {

    @DisplayName("이름이 공백일 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void createNameFailTest(String input) {
        // given & when & then
        assertThatThrownBy(() -> new Name(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이름이 공백 아니면 성공적으로 생성된다.")
    @Test
    void createNameSuccessTest() {
        // given & when & then
        assertThatCode(() -> new Name("kaki"))
                .doesNotThrowAnyException();
    }
}
