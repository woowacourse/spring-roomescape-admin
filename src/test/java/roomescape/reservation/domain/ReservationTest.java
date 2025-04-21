package roomescape.reservation.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import roomescape.reservation.domain.exception.PastReservationException;

public class ReservationTest {

    @Test
    void 과거_시간으로_예약할_수_없다() {
        assertThatThrownBy(() -> new Reservation(
                1L, "폰트",
                LocalDateTime.of(
                        LocalDate.of(2025, 4, 20),
                        LocalTime.of(0, 0)
                )
        )).isInstanceOf(PastReservationException.class);
    }
}