package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class NameTest {

    @Test
    @DisplayName("동등성을 비교한다.")
    void equalsTest() {
        Name name = new Name("웨지");
        assertThat(name).isEqualTo(new Name("웨지"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이름이 비어있는 경우, 예외를 발생한다.")
    void blankOrNullNameTest(String name) {
        assertThatThrownBy(() -> new Name(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 공백일 수 없습니다.");
    }
}
