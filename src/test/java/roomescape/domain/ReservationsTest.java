package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @ParameterizedTest
    @DisplayName("id에 해당하는 예약이 없으면 true, 예약이 있으면 false 반환한다")
    @CsvSource(value = {"1, false", "2, true"})
    void should_return_true_when_reservation_does_not_exist_by_id(Long checkId, boolean expected) {
        // given
        Reservation reservation = ReservationFixture.RESERVATION_1_KIM_2025_04_21_10_00;
        Reservations reservations = ReservationsFixture.createEmptyReservations();
        reservations.add(reservation);

        // when
        boolean result = reservations.isNotExistById(checkId);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("id에 해당하는 예약을 삭제한다")
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
