package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {

    @Test
    @DisplayName("ReservationTime 객체의 동등성을 따질 때는 id만 확인한다.")
    void testEquals() {
        ReservationTime reservationTime1 = new ReservationTime(1L, LocalTime.of(10, 0));
        ReservationTime reservationTime2 = new ReservationTime(1L, LocalTime.of(19, 30));

        assertThat(reservationTime1).isEqualTo(reservationTime2);
    }
}
