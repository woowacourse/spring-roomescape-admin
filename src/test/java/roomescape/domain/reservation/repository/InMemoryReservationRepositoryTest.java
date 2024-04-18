package roomescape.domain.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.reservation.Reservation;
import roomescape.dto.ReservationRequest;

class InMemoryReservationRepositoryTest {
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = new InMemoryReservationRepository();
    }

    @Test
    @DisplayName("예약을 저장한다")
    void save() {
        ReservationRequest reservation = new ReservationRequest("user1", LocalDate.now(), LocalTime.now());

        reservationRepository.save(reservation.toReservation());

        List<Reservation> reservations = reservationRepository.findAll();
        assertAll(
                () -> assertThat(reservations).hasSize(1),
                () -> assertThat(reservations.get(0).getName()).isEqualTo("user1")
        );
    }

    @Test
    @DisplayName("예약을 조회한다")
    void findAll() {
        ReservationRequest reservation1 = new ReservationRequest("user1", LocalDate.now(), LocalTime.now());
        ReservationRequest reservation2 = new ReservationRequest("user2", LocalDate.now(), LocalTime.now());

        reservationRepository.save(reservation1.toReservation());
        reservationRepository.save(reservation2.toReservation());

        List<Reservation> reservations = reservationRepository.findAll();
        assertAll(
                () -> assertThat(reservations).hasSize(2),
                () -> assertThat(reservations.get(0).getName()).isEqualTo("user1"),
                () -> assertThat(reservations.get(1).getName()).isEqualTo("user2")
        );
    }

    @Test
    @DisplayName("예약을 삭제한다")
    void deleteById() {
        ReservationRequest reservation = new ReservationRequest("user1", LocalDate.now(), LocalTime.now());

        reservationRepository.save(reservation.toReservation());

        reservationRepository.deleteById(1L);

        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations).hasSize(0);
    }
}
