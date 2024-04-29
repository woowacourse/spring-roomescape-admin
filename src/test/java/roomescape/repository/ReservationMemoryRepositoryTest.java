package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.memory.ReservationMemoryRepository;


class ReservationMemoryRepositoryTest {

    private ReservationMemoryRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ReservationMemoryRepository();
        repository.addReservation(new Reservation("배키",
                LocalDate.of(2023, 10, 1),
                new ReservationTime(1, LocalTime.of(10, 10))));
        repository.addReservation(new Reservation("파랑",
                LocalDate.of(2023, 11, 3),
                new ReservationTime(1, LocalTime.of(10, 20))));
    }

    @DisplayName("모든 예약을 조회한다.")
    @Test
    void should_find_all_reservations() {
        List<Reservation> allReservations = repository.getAllReservations();
        Assertions.assertThat(allReservations).hasSize(2);
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void should_add_reservations() {
        ReservationTime reservationTime = new ReservationTime(1, LocalTime.of(10, 10));
        repository.addReservation(new Reservation("배키",
                LocalDate.of(2023, 10, 1),
                reservationTime));
        List<Reservation> allReservations = repository.getAllReservations();
        Assertions.assertThat(allReservations).hasSize(3);
    }

    @DisplayName("예약을 삭제한다.")
    @Test
    void should_delete_reservation() {
        ReservationTime reservationTime = new ReservationTime(1, LocalTime.of(10, 10));
        repository.addReservation(new Reservation("배키",
                LocalDate.of(2023, 10, 1),
                reservationTime));
        repository.deleteReservation(1);
        List<Reservation> allReservations = repository.getAllReservations();
        Assertions.assertThat(allReservations).hasSize(2);
    }
}
