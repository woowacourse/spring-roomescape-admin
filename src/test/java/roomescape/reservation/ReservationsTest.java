package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReservationsTest {

    private static final long DUMMY_ID = 0L;

    @DisplayName("예약을 입력받아 저장한다.")
    @Test
    void save1() {
        // given
        final Reservations reservations = new Reservations();
        final Reservation reservation = new Reservation(DUMMY_ID, "검프",
                LocalDate.of(2025, 4, 4), LocalTime.of(14, 28));

        // when
        reservations.save(reservation);

        // then
        assertThat(reservations.getReservations()).hasSize(1);
    }

    @DisplayName("예약이 저장되면 아이디를 붙인 예약을 반환한다.")
    @Test
    void save2() {
        // given
        final Reservations reservations = new Reservations();
        final Reservation reservation = new Reservation(DUMMY_ID, "검프",
                LocalDate.of(2025, 4, 4), LocalTime.of(14, 28));

        // when
        Reservation result = reservations.save(reservation);

        // then
        assertThat(result).isEqualTo(reservation.writeId(1L));
    }

    @DisplayName("아이디를 입력받아 예약을 삭제한다.")
    @Test
    void removeReservation1() {
        // given
        final Reservations reservations = new Reservations();
        final Reservation reservation = new Reservation(DUMMY_ID, "검프",
                LocalDate.of(2025, 4, 4), LocalTime.of(14, 28));
        final Reservation savedReservation = reservations.save(reservation);

        // when
        reservations.removeReservationById(savedReservation.id());

        // then
        assertThat(reservations.getReservations()).hasSize(0);
    }

    @DisplayName("존재하지 않는 아이디가 들어오면 예외가 발생한다.")
    @Test
    void removeReservation2() {
        // given
        final Reservations reservations = new Reservations();

        // when & then
        assertThatCode(() -> reservations.removeReservationById(1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}
