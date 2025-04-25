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

class ReservationTest {

    @DisplayName("첫 번째 생성자로 null은 들어올 수 없다.")
    @ParameterizedTest
    @MethodSource("provideConstructorArguments")
    void reservation1(
            final String name,
            final LocalDate localDate,
            final Time time
    ) {
        // given & when & then
        assertThatThrownBy(() -> new Reservation(name, localDate, time))
                .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("두 번째 생성자로 null은 들어올 수 없다.")
    @ParameterizedTest
    @MethodSource("provideConstructorArguments")
    void reservation2(
            final String name,
            final LocalDate localDate,
            final Time time
    ) {
        // given & when & then
        assertAll(
                () -> assertThatThrownBy(
                        () -> new Reservation(null, "hotteok", LocalDate.now(), new Time(LocalTime.now())))
                        .isInstanceOf(NullPointerException.class),
                () -> assertThatThrownBy(
                        () -> new Reservation(1L, name, localDate, time))
                        .isInstanceOf(NullPointerException.class)
        );
    }

    private static Stream<Arguments> provideConstructorArguments() {
        return Stream.of(
                Arguments.of(null, LocalDate.now(), new Time(LocalTime.now())),
                Arguments.of("hotteok", null, new Time(LocalTime.now())),
                Arguments.of("hotteok", LocalDate.now(), null)
        );
    }
}
