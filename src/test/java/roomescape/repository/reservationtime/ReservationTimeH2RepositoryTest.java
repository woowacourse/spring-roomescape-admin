package roomescape.repository.reservationtime;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.domain.ReservationTime;

@JdbcTest
@Import(ReservationTimeH2Repository.class)
class ReservationTimeH2RepositoryTest {

    @Autowired
    private ReservationTimeH2Repository reservationTimeH2Repository;

    @Test
    @DisplayName("ReservationTime을 저장한다.")
    void save() {
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(12, 0));

        ReservationTime saved = reservationTimeH2Repository.save(reservationTime);

        assertThat(saved.id()).isNotNull();
    }

    @Test
    @DisplayName("id에 맞는 ReservationTime을 제거한다.")
    void delete() {
        reservationTimeH2Repository.delete(11L);

        assertThat(reservationTimeH2Repository.findAll()).hasSize(2);
    }

    @Test
    @DisplayName("모든 ReservationTime을 찾는다.")
    void findAll() {
        List<ReservationTime> found = reservationTimeH2Repository.findAll();

        assertThat(found).hasSize(3);
    }

    @Test
    @DisplayName("id에 맞는 ReservationTime을 찾는다.")
    void findBy() {
        ReservationTime found = reservationTimeH2Repository.findById(10L).get();

        assertThat(found.startAt()).isEqualTo(LocalTime.of(9, 0));
    }

    @Test
    @DisplayName("존재하지 않는 id가 들어오면 빈 Optional 객체를 반환한다.")
    void findEmpty() {
        Optional<ReservationTime> reservationTime = reservationTimeH2Repository.findById(-1L);

        assertThat(reservationTime.isEmpty()).isTrue();
    }
}
