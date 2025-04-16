package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReservationsTest {

    @DisplayName("예약을 입력받아 저장한다.")
    @Test
    void saveReservation1() {
        // given
        final Reservations reservations = new Reservations();
        final Reservation reservation = new Reservation("검프",
                LocalDate.of(2025, 4, 4), LocalTime.of(14, 28));

        // when
        reservations.saveReservation(reservation);

        // then
        assertThat(reservations.getReservations()).hasSize(1);
    }

    @DisplayName("예약이 저장되면 아이디를 붙인 예약을 반환한다.")
    @Test
    void saveReservation2() {
        // given
        final Reservations reservations = new Reservations();
        final Reservation reservation = new Reservation("검프",
                LocalDate.of(2025, 4, 4), LocalTime.of(14, 28));

        // when
        Reservation result = reservations.saveReservation(reservation);

        // then
        assertThat(result).isEqualTo(new Reservation(1, reservation));
    }

    @DisplayName("아이디를 입력받아 예약을 삭제한다.")
    @Test
    void removeReservation1() {
        // given
        final Reservations reservations = new Reservations();
        final Reservation reservation = new Reservation("검프",
                LocalDate.of(2025, 4, 4), LocalTime.of(14, 28));
        final Reservation savedReservation = reservations.saveReservation(reservation);

        // when
        reservations.removeReservation(savedReservation.getId());

        // then
        assertThat(reservations.getReservations()).hasSize(0);
    }

    @DisplayName("존재하지 않는 아이디가 들어오면 예외가 발생한다.")
    @Test
    void removeReservation2() {
        // given
        final Reservations reservations = new Reservations();

        // when & then
        assertThatCode(() -> reservations.removeReservation(1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}
