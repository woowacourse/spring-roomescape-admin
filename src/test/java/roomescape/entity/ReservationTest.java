package roomescape.entity;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationTest {

    @Test
    void 이름은_null일_수_없다() {
        assertThatThrownBy(() -> Reservation.of(1L, null, LocalDate.now(), LocalTime.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이름은_빈값일_수_없다() {
        assertThatThrownBy(() -> Reservation.of(1L, "", LocalDate.now(), LocalTime.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예약일자는_null일_수_없다() {
        assertThatThrownBy(() -> Reservation.of(1L, "듀이", null, LocalTime.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예약시간은_null일_수_없다() {
        assertThatThrownBy(() -> Reservation.of(1L, "듀이", LocalDate.now(), null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
