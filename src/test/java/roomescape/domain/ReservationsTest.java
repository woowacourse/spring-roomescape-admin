package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.fixture.ReservationFixture;
import roomescape.domain.fixture.ReservationsFixture;

class ReservationsTest {

    @Test
    @DisplayName("예약을 추가한다")
    void should_add_reservation() {
        // given
        Reservation reservation = ReservationFixture.RESERVATION_1_KIM_2025_04_21_10_00;
        Reservations reservations = ReservationsFixture.createEmptyReservations();

        // when
        reservations.add(reservation);

        // then
        List<Reservation> gotReservations = reservations.getReservations();
        int expectedSize = 1;
        assertThat(gotReservations).hasSize(expectedSize);
    }

    @Test
    @DisplayName("주어진 id와 같은 예약이 존재하면 true 반환한다")
    void should_return_true_when_reservation_exists_by_id() {
        // given
        Long id = 1L;
        Reservation reservation = ReservationFixture.RESERVATION_1_KIM_2025_04_21_10_00;
        Reservations reservations = ReservationsFixture.createEmptyReservations();
        reservations.add(reservation);

        // when
        boolean result = reservations.isExistById(id);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("주어진 id와 같은 예약이 존재하지 않으면 false 반환한다")
    void should_return_false_when_reservation_does_not_exist_by_id() {
        // given
        Reservations reservations = ReservationsFixture.createEmptyReservations();
        Long id = 1L;
        Long otherId = 2L;
        Reservation reservation = ReservationFixture.RESERVATION_1_KIM_2025_04_21_10_00;
        reservations.add(reservation);

        // when
        boolean result = reservations.isExistById(otherId);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("주어진 id와 같은 예약을 삭제한다")
    void should_delete_reservation_by_id() {
        // given
        Long deleteId = 1L;
        Reservation reservation1 = ReservationFixture.RESERVATION_1_KIM_2025_04_21_10_00;
        Reservation reservation2 = ReservationFixture.RESERVATION_2_Lee_2025_04_22_10_00;
        Reservations reservations = ReservationsFixture.createEmptyReservations();
        reservations.add(reservation1);
        reservations.add(reservation2);

        // when
        reservations.deleteBy(deleteId);

        // then
        List<Reservation> gotReservations = reservations.getReservations();
        int expectedSize = 1;
        assertThat(gotReservations).hasSize(expectedSize);
    }
}
