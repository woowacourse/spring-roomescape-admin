package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ReservationTest {
    @Test
    void isSameId() {
        // given
        Reservation reservation = new Reservation(1L, "히스타", LocalDate.now(), LocalTime.now());

        // when
        boolean actual = reservation.isSameId(1L);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    private static Stream<Arguments> getInvalidReservations() {
        return Stream.of(
                Arguments.of((Supplier<Reservation>) () -> new Reservation(1L, null, LocalDate.now(), LocalTime.now())),
                Arguments.of((Supplier<Reservation>) () -> new Reservation(2L, "히스타", null, LocalTime.now())),
                Arguments.of((Supplier<Reservation>) () -> new Reservation(3L, "히스타", LocalDate.now(), null))
        );
    }

    @ParameterizedTest
    @MethodSource("getInvalidReservations")
    void validate(Supplier<Reservation> reservationSupplier) {
        // when & then
        Assertions.assertThatNullPointerException().isThrownBy(
                reservationSupplier::get
        );
    }

    @DisplayName("name이 빈 값일 때(공백) IllegalArgumentException을 throw 한다.")
    @Test
    void validateBlankName() {
        // given
        Supplier<Reservation> supplier = () -> new Reservation(1L, " ", LocalDate.now(), LocalTime.now());

        // when & then
        Assertions.assertThatIllegalArgumentException().isThrownBy(
                supplier::get
        );
    }
}
