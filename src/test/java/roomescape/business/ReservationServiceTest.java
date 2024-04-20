package roomescape.business;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationResponse;
import roomescape.persistence.ReservationCollectionRepository;
import roomescape.persistence.ReservationRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static roomescape.TestFixture.*;

class ReservationServiceTest {
    private final ReservationRepository reservationRepository = new ReservationCollectionRepository();
    private final ReservationService reservationService = new ReservationService(reservationRepository);

    @Test
    @DisplayName("예약을 생성한다.")
    void createReservation() {
        // given
        Reservation reservation = MIA_RESERVATION(new ReservationTime(MIA_RESERVATION_TIME));

        // when
        ReservationResponse savedReservation = reservationService.createReservation(reservation);

        // then
        assertThat(savedReservation.id()).isNotNull();
    }

    @Test
    @DisplayName("동일한 시간에 같은 사용자가 예약할 수 없다.")
    void createSameReservation() {
        // given
        Reservation miaReservation = MIA_RESERVATION(new ReservationTime(MIA_RESERVATION_TIME));
        reservationRepository.save(miaReservation);

        Reservation newReservation = MIA_RESERVATION(new ReservationTime(MIA_RESERVATION_TIME));

        // when & then
        assertThatThrownBy(() -> reservationService.createReservation(newReservation))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("동일한 시간대에 최대 4팀이 예약할 수 있다. 초과되면 예외가 발생한다.")
    void createLimitedReservations() {
        // given
        Reservation miaReservation = MIA_RESERVATION(new ReservationTime(MIA_RESERVATION_TIME));
        Reservation tommyReservation = new Reservation(USER_TOMMY, MIA_RESERVATION_DATE, new ReservationTime(MIA_RESERVATION_TIME));
        Reservation wonnyReservation = new Reservation("wonny", MIA_RESERVATION_DATE, new ReservationTime(MIA_RESERVATION_TIME));
        Reservation neoReservation = new Reservation("neo", MIA_RESERVATION_DATE, new ReservationTime(MIA_RESERVATION_TIME));
        reservationRepository.save(miaReservation);
        reservationRepository.save(tommyReservation);
        reservationRepository.save(wonnyReservation);
        reservationRepository.save(neoReservation);

        Reservation newReservation = new Reservation("new", MIA_RESERVATION_DATE, new ReservationTime(MIA_RESERVATION_TIME));

        // when & then
        assertThatThrownBy(() -> reservationService.createReservation(newReservation))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("모든 예약 목록을 조회한다.")
    void getReservations() {
        // given
        Reservation miaReservation = MIA_RESERVATION(new ReservationTime(MIA_RESERVATION_TIME));
        Reservation tommyReservation = TOMMY_RESERVATION();
        reservationRepository.save(miaReservation);
        reservationRepository.save(tommyReservation);

        // when
        List<ReservationResponse> reservations = reservationService.getReservations();

        // then
        assertThat(reservations).hasSize(2)
                .extracting(ReservationResponse::name)
                .containsExactly(USER_MIA, USER_TOMMY);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void deleteReservation() {
        // given
        Reservation miaReservation = MIA_RESERVATION(new ReservationTime(MIA_RESERVATION_TIME));
        reservationRepository.save(miaReservation);

        // when
        reservationService.deleteReservation(1L);

        // then
        Optional<Reservation> deleted = reservationRepository.findById(1L);
        assertThat(deleted).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 예약 Id로 삭제할 수 없다.")
    void deleteReservationNotExistingId() {
        // given
        Long notExistingId = 1L;

        // when & then
        assertThatThrownBy(() -> reservationService.deleteReservation(notExistingId))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
