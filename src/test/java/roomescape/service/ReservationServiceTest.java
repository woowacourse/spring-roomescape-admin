package roomescape.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationServiceTest {
    private final ReservationRepository reservationRepository = new ReservationRepository();
    private final ReservationService reservationService = new ReservationService(reservationRepository);

    @Test
    @DisplayName("예약을 생성한다.")
    void createReservation() {
        // given
        Reservation reservation = new Reservation("미아", LocalDate.now(), LocalTime.now());

        // when
        Reservation savedReservation = reservationService.createReservation(reservation);

        // then
        assertThat(savedReservation).isNotNull();
    }

    @Test
    @DisplayName("모든 예약 목록을 조회한다.")
    void getReservations() {
        // given
        Reservation miaReservation = new Reservation("미아", LocalDate.now(), LocalTime.now());
        Reservation tommyReservation = new Reservation("토미", LocalDate.now(), LocalTime.now());
        reservationRepository.save(miaReservation);
        reservationRepository.save(tommyReservation);

        // when
        List<Reservation> reservations = reservationService.getReservations();

        // then
        assertThat(reservations).hasSize(2)
                .extracting(Reservation::getName)
                .containsExactly("미아", "토미");
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void deleteReservation() {
        // given
        Reservation miaReservation = new Reservation("미아", LocalDate.now(), LocalTime.now());
        reservationRepository.save(miaReservation);

        // when
        reservationService.deleteReservation(1L);

        // then
        Optional<Reservation> deleted = reservationRepository.findById(1L);
        assertThat(deleted).isEmpty();
    }
}
