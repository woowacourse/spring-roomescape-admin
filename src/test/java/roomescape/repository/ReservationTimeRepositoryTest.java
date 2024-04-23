package roomescape.repository;

import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.data.vo.ReservationTime;
import roomescape.fixture.FakeReservationTimeRepository;

class ReservationTimeRepositoryTest {
    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {
        reservationTimeRepository = new FakeReservationTimeRepository();
    }

    @Test
    @DisplayName("예약을 저장한다.")
    void add_ShouldSaveReservation_WhenHasNoException() {
        reservationTimeRepository.add(new ReservationTime(0L, LocalTime.now()));

        Assertions.assertThat(reservationTimeRepository.getAll()).hasSize(2);
    }

    @Test
    @DisplayName("예약을 조회한다.")
    void getAll_ShouldReturnAllReservations_WhenHasNoException() {
        reservationTimeRepository.add(new ReservationTime(LocalTime.of(2,2)));
        final var sut = reservationTimeRepository.getAll().get(1);

        Assertions.assertThat(sut).isEqualTo(new ReservationTime(2, LocalTime.of(2,2)));
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void remove_ShouldDeleteStoredDate_WhenHasNoException() {
        // Given
        final var storedId = reservationTimeRepository.add(new ReservationTime(LocalTime.now()));

        // When
        reservationTimeRepository.remove(storedId);

        // Then
        Assertions.assertThat(reservationTimeRepository.getAll()).hasSize(1);
    }

    @Test
    @DisplayName("예약 단건 조회")
    void get_ShouldReturnOneReservation_WhenHasId() {
        final var findReservation = reservationTimeRepository.get(1L);

        Assertions.assertThat(findReservation).isEqualTo(new ReservationTime(1, LocalTime.of(1, 1)));
    }
}

