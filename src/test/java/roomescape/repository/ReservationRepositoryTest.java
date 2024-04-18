package roomescape.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static roomescape.TestFixture.*;

class ReservationRepositoryTest {
    private final ReservationRepository reservationRepository = new ReservationRepository();

    @Test
    @DisplayName("예약을 저장한다.")
    void save() {
        // given
        Reservation reservation = new Reservation(USER_MIA, MIA_RESERVATION_DATE, MIA_RESERVATION_TIME);

        // when
        Reservation savedReservation = reservationRepository.save(reservation);

        // then
        assertThat(savedReservation).isNotNull();
    }

    @Test
    @DisplayName("동일시간대에 최대 4팀까지 예약이 가능하다.")
    void saveLimitedReservations() {
        // given
        Reservation miaReservation1 = new Reservation(USER_MIA, MIA_RESERVATION_DATE, MIA_RESERVATION_TIME);
        Reservation miaReservation2 = new Reservation(USER_MIA, MIA_RESERVATION_DATE, MIA_RESERVATION_TIME);
        Reservation miaReservation3 = new Reservation(USER_MIA, MIA_RESERVATION_DATE, MIA_RESERVATION_TIME);
        Reservation miaReservation4 = new Reservation(USER_MIA, MIA_RESERVATION_DATE, MIA_RESERVATION_TIME);
        reservationRepository.save(miaReservation1);
        reservationRepository.save(miaReservation2);
        reservationRepository.save(miaReservation3);
        reservationRepository.save(miaReservation4);

        Reservation newReservation = new Reservation(USER_TOMMY, MIA_RESERVATION_DATE, MIA_RESERVATION_TIME);

        // when & then
        assertThatThrownBy(() -> reservationRepository.save(newReservation))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("모든 예약 목록을 조회한다.")
    void findAll() {
        // given
        Reservation miaReservation = new Reservation(USER_MIA, MIA_RESERVATION_DATE, MIA_RESERVATION_TIME);
        Reservation tommyReservation = new Reservation(USER_TOMMY, TOMMY_RESERVATION_DATE, TOMMY_RESERVATION_TIME);
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
        Reservation miaReservation = new Reservation(USER_MIA, MIA_RESERVATION_DATE, MIA_RESERVATION_TIME);
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
        Reservation miaReservation = new Reservation(USER_MIA, MIA_RESERVATION_DATE, MIA_RESERVATION_TIME);
        reservationRepository.save(miaReservation);

        // when
        reservationRepository.deleteById(1L);

        // then
        Optional<Reservation> deleted = reservationRepository.findById(1L);
        assertThat(deleted).isEmpty();
    }
}
