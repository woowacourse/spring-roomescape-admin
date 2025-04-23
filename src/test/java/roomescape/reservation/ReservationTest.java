package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.time.Time;

public class ReservationTest {

    @DisplayName("id가 존재하지 않을 때 조회하면 예외가 발생한다.")
    @Test
    void id() {
        // given
        final Reservation reservation = new Reservation(null, "", LocalDate.of(2024, 12, 06),
                new Time(1L, LocalTime.of(12, 40)));

        // when & then
        assertThatThrownBy(() -> {
            reservation.id();
        }).isInstanceOf(NullPointerException.class);
    }

    @DisplayName("time이 존재하지 않을 때 조회하면 예외가 발생한다.")
    @Test
    void time() {
        // given
        final Reservation reservation = new Reservation(1L, "", LocalDate.of(2024, 12, 06), null);

        // when & then
        assertThatThrownBy(() -> {
            reservation.time();
        }).isInstanceOf(NullPointerException.class);
    }
}
