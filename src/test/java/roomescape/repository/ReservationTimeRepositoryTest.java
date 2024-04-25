package roomescape.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import roomescape.entity.ReservationTime;

import javax.sql.DataSource;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(scripts = {"/sample.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/drop.sql", "/schema.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@JdbcTest
class ReservationTimeRepositoryTest {

    private final ReservationTimeRepository timeRepository;

    @Autowired
    ReservationTimeRepositoryTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.timeRepository = new ReservationTimeH2Repository(jdbcTemplate, dataSource);
    }

    @Test
    @DisplayName("모든 예약 시간 목록을 조회한다.")
    void findAll() {
        // given
        List<ReservationTime> expected = List.of(
                new ReservationTime(1L, LocalTime.of(10, 15)),
                new ReservationTime(2L, LocalTime.of(11, 20)),
                new ReservationTime(3L, LocalTime.of(12, 25))
        );

        // when
        List<ReservationTime> actual = timeRepository.findAll();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("예약 시간 정보를 저장하면 새로운 아이디가 부여된다.")
    void save() {
        // given
        ReservationTime time = new ReservationTime(null, LocalTime.of(13, 30));
        ReservationTime expected = new ReservationTime(4L, LocalTime.of(13, 30));

        // when
        ReservationTime actual = timeRepository.save(time);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("등록된 예약 시간 번호로 삭제한다.")
    void deleteAssignedId() {
        // given
        Long id = 3L;

        // when & then
        assertThat(timeRepository.findById(id)).isPresent();
        assertThat(timeRepository.deleteById(id)).isNotZero();
        assertThat(timeRepository.findById(id)).isEmpty();
    }

    @Test
    @DisplayName("없는 예약 시간 번호로 삭제할 경우 아무런 영향이 없다.")
    void deleteNotExistId() {
        // given
        Long id = 4L;

        // when & then
        assertThat(timeRepository.findById(id)).isEmpty();
        assertThat(timeRepository.deleteById(id)).isZero();
    }
}
