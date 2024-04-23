package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.data.vo.Reservation;
import roomescape.data.vo.Reservation.Builder;
import roomescape.data.vo.ReservationTime;
import roomescape.fixture.FakeReservationRepository;

class ReservationRepositoryTest {

    private final ReservationRepository reservationRepository = new FakeReservationRepository();

    @Test
    @DisplayName("예약을 저장한다.")
    void add_ShouldSaveReservation_WhenHasNoException() {
        reservationRepository.add(new Reservation.Builder()
                .date(LocalDate.now())
                .name("dummy2")
                .time(new ReservationTime(0, LocalTime.now()))
                .build());

        Assertions.assertThat(reservationRepository.getAll()).hasSize(1);
    }

    @Test
    @DisplayName("예약을 조회한다.")
    void getAll_ShouldReturnAllReservations_WhenHasNoException() {
        final var expected = new Builder()
                .date(LocalDate.now())
                .name("dummy2")
                .time(new ReservationTime(0, LocalTime.now()))
                .build();

        reservationRepository.add(expected);
         final var sut = reservationRepository.getAll().get(0);

         Assertions.assertThat(expected).isEqualTo(sut);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void remove_ShouldDeleteStoredDate_WhenHasNoException() {
        // Given
        final var expected = new Builder()
                .date(LocalDate.now())
                .name("dummy2")
                .time(new ReservationTime(0, LocalTime.now()))
                .build();

        long storedId = reservationRepository.add(expected);

        // When
        reservationRepository.remove(storedId);

        // Then
        Assertions.assertThat(reservationRepository.getAll()).isEmpty();
    }
}
