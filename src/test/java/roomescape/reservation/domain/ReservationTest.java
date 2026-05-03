package roomescape.reservation.domain;

import org.junit.jupiter.api.Test;
import roomescape.time.domain.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {

    @Test
    void 예약을_생성한다() {
        ReservationTime time = new ReservationTime(1L, LocalTime.of(10, 0));

        Reservation reservation = new Reservation(1L, "브라운", LocalDate.of(2026, 5, 1), time);

        assertThat(reservation.getName()).isEqualTo("브라운");
        assertThat(reservation.getDate()).isEqualTo(LocalDate.of(2026, 5, 1));
        assertThat(reservation.getTime()).isEqualTo(time);
    }

    @Test
    void 예약자_이름은_비어있을_수_없다() {
        ReservationTime time = new ReservationTime(1L, LocalTime.of(10, 0));

        assertThatThrownBy(() -> new Reservation(1L, "", LocalDate.of(2026, 5, 1), time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예약자_이름은_null일_수_없다() {
        ReservationTime time = new ReservationTime(1L, LocalTime.of(10, 0));

        assertThatThrownBy(() -> new Reservation(1L, null, LocalDate.of(2026, 5, 1), time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예약_날짜는_null일_수_없다() {
        ReservationTime time = new ReservationTime(1L, LocalTime.of(10, 0));

        assertThatThrownBy(() -> new Reservation(1L, "브라운", null, time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예약_시간은_null일_수_없다() {
        assertThatThrownBy(() -> new Reservation(1L, "브라운", LocalDate.of(2026, 5, 1), null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
