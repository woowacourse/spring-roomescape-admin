package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

class ReservationMemoryRepositoryTest {

    private final ReservationMemoryRepository reservationMemoryRepository = new ReservationMemoryRepository();

    @Test
    @DisplayName("예약을 추가한다.")
    void add() {
        Reservation reservation = new Reservation("kargo",
                LocalDate.of(2024, 4, 1),
                LocalTime.of(14, 40));

        reservationMemoryRepository.add(reservation);

        assertThat(reservationMemoryRepository.findAllWithId().get(1L)).isEqualTo(reservation);
    }

    @Test
    @DisplayName("id와 매칭되는 예약을 삭제한다.")
    void remove() {
        //given
        Reservation reservation1 = new Reservation("brown",
                LocalDate.of(2024, 4, 1),
                LocalTime.of(14, 30));
        Reservation reservation2 = new Reservation("neo",
                LocalDate.of(2023, 12, 15),
                LocalTime.of(1, 0));
        Reservation reservation3 = new Reservation("solar",
                LocalDate.of(2024, 4, 15),
                LocalTime.of(17, 13));

        reservationMemoryRepository.add(reservation1);
        reservationMemoryRepository.add(reservation2);
        reservationMemoryRepository.add(reservation3);

        //when
        reservationMemoryRepository.remove(2L);
        Map<Long, Reservation> reservations = reservationMemoryRepository.findAllWithId();

        //then
        assertAll(
                () -> assertThat(reservations.get(1L)).isEqualTo(reservation1),
                () -> assertThat(reservations.get(2L)).isEqualTo(null),
                () -> assertThat(reservations.get(3L)).isEqualTo(reservation3)
        );
    }

    @Test
    @DisplayName("모든 예약을 id와 매칭해서 반환한다.")
    void findAllWithId() {
        //given
        Reservation reservation1 = new Reservation("brown",
                LocalDate.of(2024, 4, 1),
                LocalTime.of(14, 30));
        Reservation reservation2 = new Reservation("neo",
                LocalDate.of(2023, 12, 15),
                LocalTime.of(1, 0));
        Reservation reservation3 = new Reservation("solar",
                LocalDate.of(2024, 4, 15),
                LocalTime.of(17, 13));

        reservationMemoryRepository.add(reservation1);
        reservationMemoryRepository.add(reservation2);
        reservationMemoryRepository.add(reservation3);

        //when
        Map<Long, Reservation> reservations = reservationMemoryRepository.findAllWithId();

        //then
        assertAll(
                () -> assertThat(reservations.size()).isEqualTo(3),
                () -> assertThat(reservations.get(1L)).isEqualTo(reservation1),
                () -> assertThat(reservations.get(2L)).isEqualTo(reservation2),
                () -> assertThat(reservations.get(3L)).isEqualTo(reservation3)
        );
    }
}
