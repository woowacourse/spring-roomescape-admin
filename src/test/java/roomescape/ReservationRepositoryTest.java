package roomescape;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationRepositoryTest {

    ReservationRepository reservationRepository = ReservationRepository.getInstance();

    @AfterEach
    void afterEach() {
        reservationRepository.clear();
    }

    @Test
    void 예약_저장() {
        //given
        Reservation reservation = new Reservation("ted", "2024-01-01", "00:00");

        //when
        reservationRepository.save(reservation);
        Reservation savedReservation = reservationRepository.findById(reservation.getId());

        //then
        assertThat(savedReservation).isEqualTo(reservation);
    }

    @Test
    void 전체_예약_조회() {
        //given
        Reservation reservation1 = new Reservation("ted", "2024-01-01", "00:00");
        Reservation reservation2 = new Reservation("ted", "2024-01-02", "00:00");
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        //when
        List<Reservation> reservations = reservationRepository.findAll();

        //then
        assertThat(reservations).containsExactlyInAnyOrder(reservation1, reservation2);
    }

    @Test
    void 예약_삭제() {
        //given
        Reservation reservation = new Reservation("ted", "2024-01-01", "00:00");
        reservationRepository.save(reservation);

        //when
        reservationRepository.delete(reservation.getId());
        List<Reservation> reservations = reservationRepository.findAll();

        //then
        assertThat(reservations).doesNotContain(reservation);
    }
}
