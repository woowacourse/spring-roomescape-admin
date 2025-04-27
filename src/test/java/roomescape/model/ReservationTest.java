package roomescape.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    @DisplayName("Reservation에 id를 삽입한 객체를 만들 수 있다")
    void withId() {
        // given
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.now());
        Reservation reservation = new Reservation(null, "moko", LocalDate.now(), reservationTime);

        // when
        var expected = reservation.withId(10L);

        // then
        assertThat(expected.id()).isEqualTo(10L);
    }

    @Test
    @DisplayName("Reservation에 ReservationTime을 삽입한 객체를 만들 수 있다")
    void withReservationTime() {
        // given
        Reservation reservation = new Reservation(null, "moko", LocalDate.now(), null);
        ReservationTime time = new ReservationTime(10L, LocalTime.of(1, 10));

        // when
        var withTime = reservation.withReservationTime(time);

        // then
        assertThat(withTime.reservationTime()).isEqualTo(time);
    }
}
