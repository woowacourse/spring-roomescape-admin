package roomescape.domain.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dto.ReservationDto;

class InMemoryReservationRepositoryTest {
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = new InMemoryReservationRepository();
    }

    @Test
    @DisplayName("예약을 저장한다")
    void save() {
        ReservationDto reservation = new ReservationDto("user1", "2021-07-01", "09:00");

        reservationRepository.save(reservation);

        List<Reservation> reservations = reservationRepository.findAll();
        assertAll(
                () -> assertThat(reservations).hasSize(1),
                () -> assertThat(reservations.get(0).getName()).isEqualTo("user1")
        );
    }

    @Test
    @DisplayName("예약을 조회한다")
    void findAll() {
        ReservationDto reservation1 = new ReservationDto("user1", "2021-07-01", "09:00");
        ReservationDto reservation2 = new ReservationDto("user2", "2021-07-02", "10:00");

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

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
        ReservationDto reservation = new ReservationDto("user1", "2021-07-01", "09:00");
        reservationRepository.save(reservation);

        reservationRepository.deleteById(1L);

        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations).hasSize(0);
    }
}
