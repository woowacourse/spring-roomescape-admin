package roomescape.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class PersonTest {
    @DisplayName("비어있는 이름이라면 예외를 발생시킵니다.")
    @ParameterizedTest
    @EmptySource
    void validateEmptyNameTest(String name) {
        assertThatCode(() -> new Person(name)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이름이 7자를 초과한다면 예외를 발생시킵니다.")
    @ParameterizedTest
    @ValueSource(strings = {"하하하하하하하하", "김수한무와거북이", "삼천갑자와동방삭"})
    void validateNameLengthTest(String name) {
        assertThatCode(() -> new Person(name)).isInstanceOf(IllegalArgumentException.class);
    }
}
