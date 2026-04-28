package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NameTest {
    @Test
    @DisplayName("사용자의 이름이 10자를 초과하는 경우 예외를 발생한다.")
    void throwException_When_NameOverThreshold() {
        assertThatThrownBy(() -> new Name("가나다라마바사아자차카타파하"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("사용자의 이름이 빈 경우 예외를 발생한다.")
    void throwException_When_EmptyName() {
        assertThatThrownBy(() -> new Name(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("사용자의 이름이 null인 경우 예외를 발생한다.")
    void throwException_When_NameIsNull() {
        assertThatThrownBy(() -> new Name(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("사용자의 이름 뒤에 공백이 존재하는 경우 공백을 제거한다.")
    void removeEndSpaceInName() {
        Name name = new Name("이름 ");
        Name expected = new Name("이름");

        assertThat(name).isEqualTo(expected);
        assertEquals(expected, name);
    }

    @Test
    @DisplayName("사용자의 이름 맨 앞에 공백이 존재하는 경우 공백을 제거한다.")
    void removeStartSpaceInName() {
        Name name = new Name("  이름");
        Name expected = new Name("이름");

        assertThat(name).isEqualTo(expected);
        assertEquals(expected, name);
    }
}
