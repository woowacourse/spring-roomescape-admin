package roomescape.reservation;


import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import roomescape.reservationtime.ReservationTime;

class ReservationTest {

    @Test
    void 동등성_검증() {
        ReservationTime reservationTime = new ReservationTime(LocalTime.parse("12:00"));
        Reservation reservation1 = new Reservation(1L, "봉구스", LocalDate.parse("2026-05-03"), reservationTime);
        Reservation reservation2 = new Reservation(1L, "봉구스", LocalDate.parse("2026-05-03"), reservationTime);
        Assertions.assertThat(reservation1).isEqualTo(reservation2);
    }
}
