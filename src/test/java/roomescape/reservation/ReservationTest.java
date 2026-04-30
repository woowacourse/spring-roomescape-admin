package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import roomescape.reservationTime.ReservationTime;

class ReservationTest {

    @Test
    void 예약_생성() {
        ReservationTime time = new ReservationTime(1L, LocalTime.of(15, 40));
        Reservation reservation = new Reservation(1L, "브라운", LocalDate.of(2023, 8, 5), time);

        assertThat(reservation.getId()).isEqualTo(1L);
        assertThat(reservation.getName()).isEqualTo("브라운");
        assertThat(reservation.getDate()).isEqualTo(LocalDate.of(2023, 8, 5));
        assertThat(reservation.getTime()).isEqualTo(time);
    }
}
