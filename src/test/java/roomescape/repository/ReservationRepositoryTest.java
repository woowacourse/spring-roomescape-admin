package roomescape.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationRepositoryTest {
    private final ReservationRepository reservationRepository = new ReservationRepository();

    @Test
    @DisplayName("예약을 저장한다.")
    void save() {
        // given
        Reservation reservation = new Reservation("미아", LocalDate.now(), LocalTime.now());

        // when
        reservationRepository.save(reservation);

        // then
        Optional<Reservation> savedReservation = reservationRepository.findById(1L);
        assertThat(savedReservation).isPresent();
    }

    @Test
    @DisplayName("모든 예약 목록을 조회한다.")
    void findAll() {
        // given
        Reservation miaReservation = new Reservation("미아", LocalDate.now(), LocalTime.now());
        Reservation tommyReservation = new Reservation("토미", LocalDate.now(), LocalTime.now());
        reservationRepository.save(miaReservation);
        reservationRepository.save(tommyReservation);

        // when
        List<Reservation> reservations = reservationRepository.findAll();

        // then
        assertThat(reservations).hasSize(2)
                .extracting(Reservation::getName)
                .containsExactly("미아", "토미");
    }

    @Test
    @DisplayName("Id로 예약을 조회한다.")
    void findById() {
        // given
        Reservation miaReservation = new Reservation("미아", LocalDate.now(), LocalTime.now());
        reservationRepository.save(miaReservation);

        // when
        Optional<Reservation> actualReservation = reservationRepository.findById(1L);

        // then
        assertAll(
                () -> assertThat(actualReservation).isPresent(),
                () -> assertThat(actualReservation.get().getName()).isEqualTo("미아")
        );
    }

    @Test
    @DisplayName("Id로 예약을 삭제한다.")
    void deleteById() {
        // given
        Reservation miaReservation = new Reservation("미아", LocalDate.now(), LocalTime.now());
        reservationRepository.save(miaReservation);

        // when
        reservationRepository.deleteById(1L);

        // then
        Optional<Reservation> deleted = reservationRepository.findById(1L);
        assertThat(deleted).isEmpty();
    }

}
