package roomescape.user.reservation.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ReservationTest {

    @Nested
    class ValidCases {

        @DisplayName("id가 같으면 동일한 Reservation으로 간주한다.")
        @Test
        void equalsAndHashCode() {
            // given
            var reservationWithSameId1 = new Reservation(1L, "브라운", LocalDate.of(2023, 8, 5),
                    new ReservationTime(null, LocalTime.of(10, 0)));
            var reservationWithSameId2 = new Reservation(1L, "브라운", LocalDate.of(2023, 8, 6),
                    new ReservationTime(null, LocalTime.of(10, 0)));

            // when & then
            assertSoftly(softly -> {
                softly.assertThat(reservationWithSameId1).isEqualTo(reservationWithSameId2);
                softly.assertThat(reservationWithSameId1.hashCode()).isEqualTo(reservationWithSameId2.hashCode());
            });
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("name, date, time 중 하나라도 null이거나 name이 blank면 예외가 발생한다.")
        @ParameterizedTest
        @MethodSource("provideInvalidArguments")
        void validateNotNull(
                String invalidName,
                LocalDate invalidDate,
                ReservationTime invalidTime,
                String expectedMessage
        ) {
            // when & then
            assertThatThrownBy(() -> new Reservation(1L, invalidName, invalidDate, invalidTime))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(expectedMessage);
        }

        static Stream<Arguments> provideInvalidArguments() {
            return Stream.of(
                    Arguments.of(null, LocalDate.now(), new ReservationTime(null, LocalTime.of(10, 0)),
                            "Name cannot be null or blank"),
                    Arguments.of(" ", LocalDate.now(), new ReservationTime(null, LocalTime.of(10, 0)),
                            "Name cannot be null or blank"),
                    Arguments.of("브라운", null, new ReservationTime(null, LocalTime.of(10, 0)),
                            "Date cannot be null"),
                    Arguments.of("브라운", LocalDate.now(), null,
                            "Time cannot be null")
            );
        }
    }
}
