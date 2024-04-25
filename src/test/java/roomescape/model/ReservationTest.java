package roomescape.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static roomescape.util.Fixture.JOJO_RESERVATION_BEFORE_SAVE;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ReservationTest {

    @DisplayName("빈 값이나 유효하지 않은 값이 포함되면 예외를 던진다")
    @ParameterizedTest
    @MethodSource("provideEmptyValues")
    void throwExceptionWhenReservationIsInvalid(Long id, String name, String date, ReservationTime time) {
        assertThatThrownBy(() -> Reservation.of(id, name, date, time))
            .isInstanceOf(IllegalArgumentException.class);
    }

    public static Stream<Arguments> provideEmptyValues() {
        return Stream.of(
            Arguments.of(1L, "", "2024-04-23", ReservationTime.of(1L, "10:00")),
            Arguments.of(1L, null, "2024-04-23", ReservationTime.of(1L, "10:00")),
            Arguments.of(1L, "jojo", null, ReservationTime.of(1L, "10:00")),
            Arguments.of(1L, "jojo", "abc", ReservationTime.of(1L, "10:00")),
            Arguments.of(1L, "jojo", "2024-04-23", null)
        );
    }

    @DisplayName("0 이하 id를 추가하면 예외를 던진다")
    @Test
    void throwExceptionWhenIdIsInvalid() {
        assertThatThrownBy(() -> JOJO_RESERVATION_BEFORE_SAVE.addId(0))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
