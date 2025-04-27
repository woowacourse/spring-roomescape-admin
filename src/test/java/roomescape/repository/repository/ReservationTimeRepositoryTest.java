package roomescape.repository.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import roomescape.TestDao;
import roomescape.model.ReservationTime;

class ReservationTimeRepositoryTest {

    private final ReservationTimeRepository repository = new ReservationTimeRepository(new TestDao());

    @Test
    @DisplayName("모든 데이터를 조회한다")
    void getAll() {
        // given
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.now());
        repository.save(reservationTime);

        // when
        List<ReservationTime> all = repository.getAll();

        // then
        assertThat(all).size().isEqualTo(1);
    }

    @Test
    @DisplayName("데이터를 저장한다")
    void save() {
        // given
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.now());

        // when
        ReservationTime saved = repository.save(reservationTime);

        // then
        assertThat(repository.getAll().getFirst()).isEqualTo(saved);
    }

    @Test
    @DisplayName("데이터를 제거한다")
    void remove() {
        // given
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.now());
        repository.save(reservationTime);

        // when
        repository.remove(1L);

        // then
        assertThat(repository.getAll()).size().isEqualTo(0);
    }
}
