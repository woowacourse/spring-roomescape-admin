package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationsTest {

    final Reservations reservations = new Reservations();

    @DisplayName("새로운 예약을 생성할 수 있다.")
    @Test
    void testCreateReservation() {
        // given
        String name = "norang";
        LocalDate date = LocalDate.of(2025, 9, 24);
        LocalTime time = LocalTime.of(9, 0, 0);
        // when
        Reservation reservation = reservations.createReservation(name, date, time);
        // then
        assertThat(reservation).isEqualTo(new Reservation(1L, name, date, time));
    }

    @DisplayName("예약을 삭제할 수 있다.")
    @Test
    void testDeleteReservation() {
        // given
        String name = "norang";
        LocalDate date = LocalDate.of(2025, 9, 24);
        LocalTime time = LocalTime.of(9, 0, 0);
        reservations.createReservation(name, date, time);
        // when
        reservations.deleteReservationById(1L);
        // then
        assertThat(reservations.getReservations()).isEmpty();
    }

    @DisplayName("예약 목록을 조회할 수 있다.")
    @Test
    void testGetReservations() {
        // given
        String name = "norang";
        LocalDate date = LocalDate.of(2025, 9, 24);
        LocalTime time = LocalTime.of(9, 0, 0);
        Reservation reservation = reservations.createReservation(name, date, time);
        // when
        // then
        assertThat(reservations.getReservations()).containsExactly(reservation);
    }
}
