package roomescape;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import roomescape.domain.Name;

class NameTest {
    @ParameterizedTest
    @CsvSource({
            "8글자입니다.."
    })
    @EmptySource
    @NullSource
    void validateLength(String name) {
        assertThatThrownBy(() -> new Name(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
