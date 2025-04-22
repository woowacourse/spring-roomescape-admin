package roomescape.entity;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationTest {

    @Test
    void 이름은_null일_수_없다() {
        assertThatThrownBy(() -> Reservation.of(1L, null, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이름은_빈값일_수_없다() {
        assertThatThrownBy(() -> Reservation.of(1L, "", LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예약일자와_시간은_null일_수_없다() {
        assertThatThrownBy(() -> Reservation.of(1L, "듀이", null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
