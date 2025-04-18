package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationsTest {

    Reservations reservations = new Reservations();

    @DisplayName("예약을 추가할 수 있다")
    @Test
    void addReservation() {
        String name = "Kim";
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        reservations.add(name, date, time);

        Reservation savedReservation = reservations.getReservations().getFirst();
        assertAll(
                () -> assertThat(savedReservation.getName()).isEqualTo(name),
                () -> assertThat(savedReservation.getDate()).isEqualTo(date),
                () -> assertThat(savedReservation.getTime()).isEqualTo(time));
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
                () -> assertThat(reservations.getReservations())
                        .extracting(Reservation::getId)
                        .doesNotContain(deleteReservationId));
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