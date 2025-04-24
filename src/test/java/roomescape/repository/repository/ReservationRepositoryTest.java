package roomescape.repository.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import roomescape.TestDao;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

class ReservationRepositoryTest {

    private final ReservationRepository repository = new ReservationRepository(new TestDao());

    @Test
    @DisplayName("모든 데이터를 조회한다")
    void getAll() {
        // given
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.now());
        Reservation saved = repository.save(new Reservation(null, "moko", LocalDate.now(), reservationTime));

        // when
        List<Reservation> all = repository.getAll();

        // then
        assertThat(all).size().isEqualTo(1);
    }

    @Test
    @DisplayName("데이터를 저장한다")
    void save() {
        // given
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.now());

        // when
        Reservation saved = repository.save(new Reservation(null, "moko", LocalDate.now(), reservationTime));

        // then
        assertThat(repository.getAll().getFirst()).isEqualTo(saved);
    }

    @Test
    @DisplayName("데이터를 제거한다")
    void remove() {
        // given
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.now());
        Reservation saved = repository.save(new Reservation(null, "moko", LocalDate.now(), reservationTime));

        // when
        repository.remove(1L);

        // then
        assertThat(repository.getAll()).size().isEqualTo(0);
    }
}
