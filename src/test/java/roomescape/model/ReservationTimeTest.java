package roomescape.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ReservationTimeTest {

    @DisplayName("빈 값이나 유효하지 않은 값이 포함되면 예외를 던진다")
    @ParameterizedTest
    @MethodSource("provideEmptyValues")
    void throwExceptionWhenTimeIsInvalid(Long id, String time) {
        assertThatThrownBy(() -> ReservationTime.of(id, time))
            .isInstanceOf(IllegalArgumentException.class);
    }

    public static Stream<Arguments> provideEmptyValues() {
        return Stream.of(
            Arguments.of(null, "10:00"),
            Arguments.of(1L, null),
            Arguments.of(1L, "abc")
        );
    }

}
