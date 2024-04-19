package roomescape.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static roomescape.TestFixture.*;

class ReservationRepositoryTest {
    private final ReservationRepository reservationRepository = new ReservationRepository();

    @Test
    @DisplayName("예약을 저장한다.")
    void save() {
        // given
        Reservation reservation = MIA_RESERVATION();

        // when
        Reservation savedReservation = reservationRepository.save(reservation);

        // then
        assertThat(savedReservation).isNotNull();
    }

    @Test
    @DisplayName("동일시간대의 예약 목록을 조회한다.")
    void saveLimitedReservations() {
        // given
        Reservation miaReservation = MIA_RESERVATION();
        Reservation wonnyReservation = new Reservation("wonny", MIA_RESERVATION_DATE, MIA_RESERVATION_TIME);
        Reservation tommyReservation = TOMMY_RESERVATION();
        reservationRepository.save(miaReservation);
        reservationRepository.save(tommyReservation);
        reservationRepository.save(wonnyReservation);

        // when
        List<Reservation> reservations = reservationRepository.findAllByDateAndTime(MIA_RESERVATION_DATE, MIA_RESERVATION_TIME);

        // then
        assertThat(reservations).hasSize(2)
                .containsExactly(miaReservation, wonnyReservation);
    }

    @Test
    @DisplayName("모든 예약 목록을 조회한다.")
    void findAll() {
        // given
        Reservation miaReservation = MIA_RESERVATION();
        Reservation tommyReservation = TOMMY_RESERVATION();
        reservationRepository.save(miaReservation);
        reservationRepository.save(tommyReservation);

        // when
        List<Reservation> reservations = reservationRepository.findAll();

        // then
        assertThat(reservations).hasSize(2)
                .extracting(Reservation::getName)
                .containsExactly(USER_MIA, USER_TOMMY);
    }

    @Test
    @DisplayName("Id로 예약을 조회한다.")
    void findById() {
        // given
        Reservation miaReservation = MIA_RESERVATION();
        reservationRepository.save(miaReservation);

        // when
        Optional<Reservation> actualReservation = reservationRepository.findById(1L);

        // then
        assertAll(
                () -> assertThat(actualReservation).isPresent(),
                () -> assertThat(actualReservation.get().getName()).isEqualTo(USER_MIA)
        );
    }

    @Test
    @DisplayName("Id로 예약을 삭제한다.")
    void deleteById() {
        // given
        Reservation miaReservation = MIA_RESERVATION();
        reservationRepository.save(miaReservation);

        // when
        reservationRepository.deleteById(1L);

        // then
        Optional<Reservation> deleted = reservationRepository.findById(1L);
        assertThat(deleted).isEmpty();
    }
}
