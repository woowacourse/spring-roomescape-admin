package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.model.Reservation;
import roomescape.model.Reservations;

class ReservationsTest {

    @DisplayName("예약을 추가할 수 있다.")
    @Test
    void can_add_reservation() {
        Reservations reservations = new Reservations();

        reservations.add(new Reservation(1L, "포스티", LocalDate.of(2025, 4, 16), LocalTime.of(11, 0)));

        assertThat(reservations.getReservations()).hasSize(1);
    }

    @DisplayName("예약을 삭제할 수 있다.")
    @Test
    void can_remove_reservation() {
        Reservations reservations = new Reservations();
        reservations.add(new Reservation(1L, "포스티", LocalDate.of(2025, 4, 16), LocalTime.of(11, 0)));

        reservations.removeById(1L);

        assertThat(reservations.getReservations()).hasSize(0);
    }

    @DisplayName("존재하지 않는 예약은 삭제할 수 없다.")
    @Test
    void do_not_delete_non_exist_reservation() {
        Reservations reservations = new Reservations();

        assertThatThrownBy(() -> reservations.removeById(1L))
                .isInstanceOf(RuntimeException.class);
    }
}
