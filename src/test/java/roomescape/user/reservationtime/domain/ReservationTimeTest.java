package roomescape.user.reservationtime.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {

    @Nested
    class ValidCases {

        @Test
        @DisplayName("id가 같으면 equals와 hashCode가 같다.")
        void equalsAndHashCode() {
            // given
            var reservationTime1 = new ReservationTime(1L, LocalTime.of(10, 0));
            var reservationTime2 = new ReservationTime(1L, LocalTime.of(12, 0));

            // when & then
            assertSoftly(softly -> {
                softly.assertThat(reservationTime1).isEqualTo(reservationTime2);
                softly.assertThat(reservationTime1.hashCode()).isEqualTo(reservationTime2.hashCode());
            });
        }
    }

    @Nested
    class InvalidCases {

        @Test
        @DisplayName("startAt이 null이면 예외가 발생한다.")
        void validateNotNull() {
            // when & then
            assertThatThrownBy(() -> new ReservationTime(1L, null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Start time cannot be null");
        }
    }
}
