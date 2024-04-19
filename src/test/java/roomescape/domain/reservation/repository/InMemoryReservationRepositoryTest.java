package roomescape.domain.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.domain.reservation.Reservation;

class InMemoryReservationRepositoryTest {
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = new InMemoryReservationRepository();
    }

    @Test
    void 예약을_저장한다() { // todo now() 제거, 시간 검증
        Reservation reservation = new Reservation("prin", LocalDate.now(), LocalTime.now());

        reservationRepository.save(reservation);

        List<Reservation> reservations = reservationRepository.findAll();
        assertAll(
                () -> assertThat(reservations).hasSize(1),
                () -> assertThat(reservations.get(0).getName()).isEqualTo("prin")
        );
    }

    @Test
    void 예약을_조회한다() {
        Reservation reservation1 = new Reservation("prin", LocalDate.now(), LocalTime.now());
        Reservation reservation2 = new Reservation("liv", LocalDate.now(), LocalTime.now());

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        List<Reservation> reservations = reservationRepository.findAll();
        assertAll(
                () -> assertThat(reservations).hasSize(2),
                () -> assertThat(reservations.get(0).getName()).isEqualTo("prin"),
                () -> assertThat(reservations.get(1).getName()).isEqualTo("liv")
        );
    }

    @Test
    void 예약을_삭제한다() {
        Reservation reservation = new Reservation("prin", LocalDate.now(), LocalTime.now());
        reservationRepository.save(reservation);

        reservationRepository.deleteById(1L);

        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations).hasSize(0);
    }
}
