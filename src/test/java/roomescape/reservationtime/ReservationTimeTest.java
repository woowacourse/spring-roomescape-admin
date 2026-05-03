package roomescape.reservationtime;

import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {
    @Test
    void 동등성_검증() {
        ReservationTime reservationTime1 = new ReservationTime(1L, LocalTime.parse("12:00"));
        ReservationTime reservationTime2 = new ReservationTime(1L, LocalTime.parse("12:00"));
        Assertions.assertThat(reservationTime1).isEqualTo(reservationTime2);
    }
}
