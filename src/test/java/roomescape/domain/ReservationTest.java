package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    @DisplayName("id가 같으면 true를 반환한다.")
    void should_return_true_when_id_is_same() {
        // given
        Long id = 1L;
        String name = "miku";
        LocalDate date = LocalDate.of(2025, 4, 21);
        LocalTime time = LocalTime.of(10, 0);
        Reservation reservation = new Reservation(id, name, date, time);

        // when
        final boolean result = reservation.isSameId(id);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("id가 다르면 false를 반환한다.")
    void should_return_false_when_id_is_different() {
        // given
        Long id = 1L;
        Long otherId = 2L;
        String name = "miku";
        LocalDate date = LocalDate.of(2025, 4, 21);
        LocalTime time = LocalTime.of(10, 0);
        Reservation reservation = new Reservation(id, name, date, time);

        // when
        final boolean result = reservation.isSameId(otherId);

        // then
        assertThat(result).isFalse();
    }
}
