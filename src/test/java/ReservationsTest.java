import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.reservation.Reservations;
import roomescape.reservation.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationsTest {

    private final Reservations reservations = new Reservations();

    @DisplayName("예약 목록 확인")
    @Test
    void checkReservations() {
        List<Reservation> additionalReservations = List.of(
                new Reservation(1L, "test1", LocalDate.of(2025, 4, 19), LocalTime.of(12, 13)),
                new Reservation(2L, "test2", LocalDate.of(2025, 4, 20), LocalTime.of(13, 13)),
                new Reservation(3L, "test3", LocalDate.of(2025, 4, 21), LocalTime.of(14, 13))
        );
        for (Reservation additionalReservation : additionalReservations) {
            reservations.add(additionalReservation);
        }

        List<Reservation> findReservations = reservations.findAll();

        Assertions.assertThat(additionalReservations).isEqualTo(findReservations);
    }

    @DisplayName("예약 추가 확인")
    @Test
    void addReservationTest() {
        Reservation reservation = new Reservation(1L, "test1", LocalDate.of(2025, 4, 19), LocalTime.of(12, 13));
        reservations.add(reservation);
        List<Reservation> findReservation = reservations.findAll();
        assertAll(
                () -> Assertions.assertThat(findReservation.size()).isEqualTo(1),
                () -> Assertions.assertThat(findReservation.getFirst()).isEqualTo(reservation)
        );
    }

    @DisplayName("예약 삭제 확인")
    @Test
    void deleteReservationTest() {
        Reservation reservation = new Reservation(1L, "test1", LocalDate.of(2025, 4, 19), LocalTime.of(12, 13));
        reservations.add(reservation);
        reservations.deleteById(1L);
        List<Reservation> findReservation = reservations.findAll();
        Assertions.assertThat(findReservation).isEmpty();
    }
}
