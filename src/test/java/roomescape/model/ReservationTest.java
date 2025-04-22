package roomescape.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

class ReservationTest {

    @DisplayName("null 존재하지 않아야 생성 가능하다.")
    @ParameterizedTest
    @MethodSource("invalidReservationArguments")
    void invalidReservationInputTest(Long id, String name, LocalDate date, LocalTime time) {
        Assertions.assertThatThrownBy(() -> new Reservation(id, name, date, time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> invalidReservationArguments() {
        return Stream.of(
                Arguments.of(null, "가이온", LocalDate.now(), LocalTime.now()),
                Arguments.of(1L, null, LocalDate.now(), LocalTime.now()),
                Arguments.of(1L, "가이온", null, LocalTime.now()),
                Arguments.of(1L, "가이온", LocalDate.now(), null)
        );
    }

    @DisplayName("공백이거나 이름이 존재하지 않는 경우 생성할 수 없다.")
    @ParameterizedTest
    @MethodSource("invalidNames")
    void invalidReservationNameTest(String reservationName) {
        Long id = 1L;
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();

        Assertions.assertThatThrownBy(() -> new Reservation(id, reservationName, localDate, localTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> invalidNames() {
        return Stream.of(
                Arguments.of(" "),
                Arguments.of("")
        );
    }
}
