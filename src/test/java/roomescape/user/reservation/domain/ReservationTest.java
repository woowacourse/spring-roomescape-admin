package roomescape.user.reservation.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class ReservationTest {

    @Nested
    class ValidCases {

        @DisplayName("id가 같으면 동일한 Reservation으로 간주한다.")
        @Test
        void equalsAndHashCode() {
            // given
            var reservationWithSameId1 = new Reservation(1L, "브라운", LocalDate.of(2023, 8, 5), 1L);
            var reservationWithSameId2 = new Reservation(1L, "브라운", LocalDate.of(2023, 8, 6), 2L);

            // when & then
            assertSoftly(softly -> {
                softly.assertThat(reservationWithSameId1)
                        .isEqualTo(reservationWithSameId2);
                softly.assertThat(reservationWithSameId1.hashCode())
                        .isEqualTo(reservationWithSameId2.hashCode());
            });
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("name, date, timeId 중 하나라도 null이거나 name이 blank면 예외가 발생한다.")
        @ParameterizedTest
        @MethodSource("provideInvalidArguments")
        void validateNotNull(
                String invalidName,
                LocalDate invalidDate,
                Long invalidTimeId,
                String expectedMessage
        ) {
            // when & then
            assertThatThrownBy(() -> new Reservation(1L, invalidName, invalidDate, invalidTimeId))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(expectedMessage);
        }

        static Stream<Object[]> provideInvalidArguments() {
            return Stream.of(
                    new Object[]{null, LocalDate.now(), 1L, "Name cannot be null or blank"},
                    new Object[]{" ", LocalDate.now(), 1L, "Name cannot be null or blank"},
                    new Object[]{"브라운", null, 1L, "Date cannot be null"},
                    new Object[]{"브라운", LocalDate.now(), null, "Time cannot be null"}
            );
        }
    }
}
