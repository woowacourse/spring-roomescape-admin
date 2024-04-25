package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTest {

    @Test
    @DisplayName("성공 : id를 통해 동일한 예약인지 판별한다.")
    void checkSameReservation_Success() {
        Reservation reservation1 = new Reservation(1L, "capy", "2024-04-25", new ReservationTime("10:00"));
        Reservation reservation2 = new Reservation(1L, "capy", "2024-04-25", new ReservationTime("10:00"));

        assertThat(reservation1.isSameReservation(reservation2.getId())).isTrue();
    }

    @Test
    @DisplayName("실패 : id를 통해 동일한 예약인지 판별한다.")
    void checkSameReservation_Failure() {
        Reservation reservation1 = new Reservation(1L, "capy", "2024-04-25", new ReservationTime("10:00"));
        Reservation reservation2 = new Reservation(2L, "capy", "2024-04-25", new ReservationTime("11:00"));

        assertThat(reservation1.isSameReservation(reservation2.getId())).isFalse();
    }
}
