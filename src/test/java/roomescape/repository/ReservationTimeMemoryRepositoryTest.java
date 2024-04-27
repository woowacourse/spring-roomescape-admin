package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.model.ReservationTime;

class ReservationTimeMemoryRepositoryTest {

    @DisplayName("저장된 모든 예약시간을 조회한다")
    @Test
    void should_find_all_reservation_times() {
        ReservationTimeMemoryRepository repository = new ReservationTimeMemoryRepository();
        List<ReservationTime> allReservationTimes = repository.findAllReservationTimes();
        assertThat(allReservationTimes).isEmpty();
    }

    @DisplayName("예약시간을 추가한다")
    @Test
    void should_add_reservation_time() {
        ReservationTimeMemoryRepository repository = new ReservationTimeMemoryRepository();
        repository.addReservationTime(new ReservationTime(LocalTime.of(10, 0)));
        List<ReservationTime> allReservationTimes = repository.findAllReservationTimes();
        assertThat(allReservationTimes).hasSize(1);
    }

    @DisplayName("예약시간을 삭제한다")
    @Test
    void should_delete_reservation_time() {
        ReservationTimeMemoryRepository repository = new ReservationTimeMemoryRepository();
        repository.addReservationTime(new ReservationTime(LocalTime.of(10, 0)));
        repository.deleteReservationTime(1);
        List<ReservationTime> allReservationTimes = repository.findAllReservationTimes();
        assertThat(allReservationTimes).isEmpty();
    }
}
