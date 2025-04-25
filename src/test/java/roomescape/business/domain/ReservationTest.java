package roomescape.business.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class ReservationTest {

    @DisplayName("생성자1로 null은 들어올 수 없다.")
    @ParameterizedTest
    @MethodSource("provideConstructorArguments")
    void validateNonNull1(
            final String name,
            final LocalDate localDate,
            final Time time
    ) {
        // given & when & then
        assertThatThrownBy(() -> new Reservation(name, localDate, time))
                .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("생성자2로 null은 들어올 수 없다.")
    @ParameterizedTest
    @MethodSource("provideConstructorArguments")
    void validateNonNull2(
            final String name,
            final LocalDate localDate,
            final Time time
    ) {
        // given & when & then
        assertAll(
                () -> assertThatThrownBy(
                        () -> new Reservation(null, "hotteok", LocalDate.MAX, new Time(LocalTime.MAX)))
                        .isInstanceOf(NullPointerException.class),
                () -> assertThatThrownBy(
                        () -> new Reservation(1L, name, localDate, time))
                        .isInstanceOf(NullPointerException.class)
        );
    }

    private static Stream<Arguments> provideConstructorArguments() {
        return Stream.of(
                Arguments.of(null, LocalDate.MAX, new Time(LocalTime.MAX)),
                Arguments.of("hotteok", null, new Time(LocalTime.MAX)),
                Arguments.of("hotteok", LocalDate.MAX, null)
        );
    }

    @DisplayName("이름은 공백이나 빈칸일 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "     "})
    void validateNameIsNotBlack(final String name) {
        // given & when & then
        assertThatThrownBy(() -> new Reservation(name, LocalDate.MAX, new Time(LocalTime.MAX)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
