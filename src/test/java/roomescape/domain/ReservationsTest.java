package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static roomescape.test.utility.ReservationTestUtility.checkReservation;
import static roomescape.test.utility.ReservationsTestUtility.checkDeleteReservation;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationsTest {

    Reservations reservations = new Reservations();

    @DisplayName("예약을 추가할 수 있다")
    @Test
    void addReservation() {
        Reservation expected = new Reservation(1L, "reservation1", LocalDate.now(), LocalTime.now());

        reservations.add(expected.getName(), expected.getDate(), expected.getTime());

        Reservation savedReservation = reservations.getReservations().getFirst();
        checkReservation(savedReservation, expected);
    }

    @DisplayName("ID를 기반으로 예약을 삭제할 수 있다")
    @Test
    void deleteReservationById() {
        reservations.add("reservation1", LocalDate.now(), LocalTime.now());
        reservations.add("reservation2", LocalDate.now(), LocalTime.now());
        reservations.add("reservation3", LocalDate.now(), LocalTime.now());
        long deleteReservationId = reservations.getReservations().getFirst().getId();

        reservations.deleteBy(deleteReservationId);
        assertAll(
                () -> assertThat(reservations.getReservations()).hasSize(2),
                () -> checkDeleteReservation(reservations, deleteReservationId));
    }

    @DisplayName("모든 예약들을 조회할 수 있다")
    @Test
    void getReservations() {
        reservations.add("reservation1", LocalDate.now(), LocalTime.now());
        reservations.add("reservation2", LocalDate.now(), LocalTime.now());
        reservations.add("reservation3", LocalDate.now(), LocalTime.now());

        assertThat(reservations.getReservations()).hasSize(3);
    }
}