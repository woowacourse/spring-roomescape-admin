package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

class MemoryReservationRepositoryTest {
    private MemoryReservationRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new MemoryReservationRepository();
    }

    @DisplayName("초기에 3개가 저장된다.")
    @Test
    void testInitialRepository() {
        assertThat(repository.findAll()).hasSize(0);
    }

    @DisplayName("예약을 추가할 수 있다")
    @Test
    void saveTest() {
        // given
        ReservationTime reservationTime = ReservationTime.of(1L, LocalTime.of(10, 0));
        Reservation reservation = Reservation.of("멍구", LocalDate.of(2000, 11, 2), reservationTime);

        // when
        repository.save(reservation);

        // then
        assertThat(repository.findAll()).hasSize(1);
    }

    @DisplayName("id로 예약을 삭제할 수 있다.")
    @Test
    void deleteByIdTest() {
        // given
        ReservationTime reservationTime = ReservationTime.of(1L, LocalTime.of(10, 0));
        Reservation reservation = Reservation.of("멍구", LocalDate.of(2000, 11, 2), reservationTime);
        Reservation reservationEntity = repository.save(reservation);

        // when
        repository.deleteById(reservationEntity.getId());

        // then
        assertThat(repository.findAll()).hasSize(0);
    }
}
