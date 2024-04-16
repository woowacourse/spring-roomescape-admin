package roomescape.domain.reservation;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InMemoryReservationRepositoryTest {
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = new InMemoryReservationRepository();
    }

    @Test
    @DisplayName("예약을 저장한다")
    void save() {
        Reservation reservation = new Reservation(1L, "user1", "2021-07-01", "09:00");

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
        Reservation reservation1 = new Reservation(1L, "user1", "2021-07-01", "09:00");
        Reservation reservation2 = new Reservation(2L, "user2", "2021-07-02", "10:00");

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        List<Reservation> reservations = reservationRepository.findAll();
        assertAll(
                () -> assertThat(reservations).hasSize(2),
                () -> assertThat(reservations.get(0).getName()).isEqualTo("user1"),
                () -> assertThat(reservations.get(1).getName()).isEqualTo("user2")
        );
    }
}
