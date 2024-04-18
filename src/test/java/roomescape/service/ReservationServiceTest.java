package roomescape.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static roomescape.TestFixture.*;

class ReservationServiceTest {
    private final ReservationRepository reservationRepository = new ReservationRepository();
    private final ReservationService reservationService = new ReservationService(reservationRepository);

    @Test
    @DisplayName("예약을 생성한다.")
    void createReservation() {
        // given
        Reservation reservation = new Reservation(USER_MIA, MIA_RESERVATION_DATE, MIA_RESERVATION_TIME);

        // when
        Reservation savedReservation = reservationService.createReservation(reservation);

        // then
        assertThat(savedReservation).isNotNull();
    }

    @Test
    @DisplayName("모든 예약 목록을 조회한다.")
    void getReservations() {
        // given
        Reservation miaReservation = new Reservation(USER_MIA, MIA_RESERVATION_DATE, MIA_RESERVATION_TIME);
        Reservation tommyReservation = new Reservation(USER_TOMMY, TOMMY_RESERVATION_DATE, TOMMY_RESERVATION_TIME);
        reservationRepository.save(miaReservation);
        reservationRepository.save(tommyReservation);

        // when
        List<Reservation> reservations = reservationService.getReservations();

        // then
        assertThat(reservations).hasSize(2)
                .extracting(Reservation::getName)
                .containsExactly(USER_MIA, USER_TOMMY);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void deleteReservation() {
        // given
        Reservation miaReservation = new Reservation(USER_MIA, MIA_RESERVATION_DATE, MIA_RESERVATION_TIME);
        reservationRepository.save(miaReservation);

        // when
        reservationService.deleteReservation(1L);

        // then
        Optional<Reservation> deleted = reservationRepository.findById(1L);
        assertThat(deleted).isEmpty();
    }
}
