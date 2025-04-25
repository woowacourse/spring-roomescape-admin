package roomescape.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;

class PersonTest {
    @DisplayName("비어있는 이름이라면 예외를 발생시킵니다.")
    @ParameterizedTest
    @EmptySource
    void invalidNameTest(String name) {
        assertThatCode(() -> new Person(name)).isInstanceOf(IllegalArgumentException.class);
    }
}
