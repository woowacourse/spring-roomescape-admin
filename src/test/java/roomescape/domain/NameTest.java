package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class NameTest {
    @Test
    @DisplayName("문자열을 통해 이름을 생성한다")
    void create_domain_with_String() {
        final String value = "조이썬";
        assertThatCode(() -> new Name(value))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"상", ""," "})
    @DisplayName("이름이 한 글자이거나 공백이면 예외를 발생한다")
    void throw_exception_when_length_less_than_two(final String value) {
        assertThatThrownBy(() -> new Name(value))
                .isInstanceOf(IllegalArgumentException.class);


    }
}
