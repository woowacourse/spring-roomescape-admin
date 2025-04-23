package roomescape.entity;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationTest {

    private static final ReservationTime TEST_TIME = ReservationTime.of(LocalTime.now());

    @Test
    void 이름은_null일_수_없다() {
        assertThatThrownBy(() -> Reservation.of(1L, null, LocalDate.now(), TEST_TIME))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이름은_빈값일_수_없다() {
        assertThatThrownBy(() -> Reservation.of(1L, "", LocalDate.now(), TEST_TIME))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예약일자는_null일_수_없다() {
        assertThatThrownBy(() -> Reservation.of(1L, "듀이", null, TEST_TIME))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
