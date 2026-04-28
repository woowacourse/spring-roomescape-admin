package roomescape;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationsTest {
    Reservations reservations;
    Reservation testReservation;

    @BeforeEach
    void setUp() {
        testReservation = new Reservation(
                "네오",
                LocalDate.of(2026, 4, 28),
                LocalTime.of(16, 0));

        reservations = new Reservations();
        reservations.add(testReservation);
    }

    @Test
    void 특정_번호를_가진_예약을_반환한다() {
        assertThat(reservations.getById(1L)).isEqualTo(testReservation);
    }

    @Test
    void 특정_번호를_가진_예약을_삭제한다() {
        reservations.deleteById(1L);
        assertThat(reservations.size()).isEqualTo(0);
    }

    @Test
    void 존재하지_않는_예약을_조회할_경우_예외를_던진다() {
        assertThatThrownBy(() -> reservations.getById(10L)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void 존재하지_않는_예약을_삭제할_경우_예외를_던진다() {
        assertThatThrownBy(() -> reservations.deleteById(10L)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void 새_예약을_추가한다() {
        Reservation newReservation = new Reservation(
                "브라운",
                LocalDate.of(2026, 4, 28),
                LocalTime.of(17, 0));

        reservations.add(newReservation);

        assertThat(reservations.size()).isEqualTo(2);
    }

    @Test
    void 날짜와_시간이_중복된_예약을_추가할_경우_예외를_던진다() {
        Reservation newReservation = new Reservation(
                "브라운",
                LocalDate.of(2026, 4, 28),
                LocalTime.of(16, 0));

        assertThatThrownBy(() -> reservations.add(newReservation))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 전체_예약_목록을_반환한다() {
        assertThat(reservations.getAllReservations()).containsExactly(testReservation);
    }
}
