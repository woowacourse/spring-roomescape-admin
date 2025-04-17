package roomescape.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationsTest {
    @Test
    @DisplayName("예약 리스트에 예약을 추가한다.")
    void addReservation() {
        Reservations reservations = new Reservations();
        Reservation reservation = new Reservation(new Id(), "moda",
                LocalDate.of(2025, 4, 17), LocalTime.of(10, 10));

        reservations.add(reservation);

        assertThat(reservations.getAll()).containsExactly(reservation);
    }

    @Test
    @DisplayName("예약 리스트에 예약을 삭제한다.")
    void deleteReservation() {
        Reservations reservations = new Reservations();
        Id id = new Id();
        Reservation reservation = new Reservation(id, "moda",
                LocalDate.of(2025, 4, 17), LocalTime.of(10, 10));
        reservations.add(reservation);

        reservations.deleteById(id);

        assertThat(reservations.getAll()).isEmpty();
    }

}
