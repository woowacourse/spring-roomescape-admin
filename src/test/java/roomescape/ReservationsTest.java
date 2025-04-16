package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReservationsTest {

    @Test
    @DisplayName("다음 아이디는 1부터 시작하며 마지막 아이디보다 1 증가한 값이다.")
    void nextId() {
        // given
        Reservations reservations = new Reservations();
        Long beforeId = reservations.nextId();

        // when
        Long nextId = reservations.nextId();

        // then
        assertAll(
                () -> assertThat(beforeId).isEqualTo(1),
                () -> assertThat(nextId).isEqualTo(2)
        );
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void addReservation() {
        // given
        Reservations reservations = new Reservations();
        Reservation reservation = new Reservation(
                1L,
                "브라운",
                LocalDate.of(2023, 12, 1),
                LocalTime.of(1, 1)
        );

        // when
        reservations.add(reservation);

        // then
        assertThat(reservations.getReservations()).containsOnly(reservation);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void removeReservation() {
        // given
        Reservations reservations = new Reservations();
        Reservation reservation = new Reservation(
                1L,
                "브라운",
                LocalDate.of(2023, 12, 1),
                LocalTime.of(1, 1)
        );
        reservations.add(reservation);

        // when
        reservations.removeById(1L);

        // then
        assertThat(reservations.getReservations()).isEmpty();
    }

    @Test
    @DisplayName("모든 예약을 조회한다.")
    void getAllReservation() {
        // given
        Reservations reservations = new Reservations();
        Reservation reservation1 = new Reservation(
                1L,
                "브라운",
                LocalDate.of(2023, 12, 1),
                LocalTime.of(1, 1)
        );
        Reservation reservation2 = new Reservation(
                2L,
                "브라운",
                LocalDate.of(2023, 12, 1),
                LocalTime.of(1, 1)
        );
        reservations.add(reservation1);
        reservations.add(reservation2);

        // when
        // then
        assertThat(reservations.getReservations()).hasSize(2);
    }
}
