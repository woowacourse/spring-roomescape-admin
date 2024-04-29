package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.model.ReservationTime;
import roomescape.repository.memory.ReservationTimeMemoryRepository;

class ReservationTimeMemoryRepositoryTest {

    private ReservationTimeMemoryRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ReservationTimeMemoryRepository();
        repository.addReservationTime(new ReservationTime(LocalTime.of(10, 0)));
        repository.addReservationTime(new ReservationTime(LocalTime.of(11, 0)));
    }

    @DisplayName("저장된 모든 예약시간을 조회한다")
    @Test
    void should_find_all_reservation_times() {
        List<ReservationTime> allReservationTimes = repository.findAllReservationTimes();
        assertThat(allReservationTimes).hasSize(2);
    }

    @DisplayName("예약시간을 추가한다")
    @Test
    void should_add_reservation_time() {
        repository.addReservationTime(new ReservationTime(LocalTime.of(10, 0)));
        List<ReservationTime> allReservationTimes = repository.findAllReservationTimes();
        assertThat(allReservationTimes).hasSize(3);
    }

    @DisplayName("예약시간을 삭제한다")
    @Test
    void should_delete_reservation_time() {
        repository.deleteReservationTime(1);
        List<ReservationTime> allReservationTimes = repository.findAllReservationTimes();
        assertThat(allReservationTimes).hasSize(1);
    }
}
