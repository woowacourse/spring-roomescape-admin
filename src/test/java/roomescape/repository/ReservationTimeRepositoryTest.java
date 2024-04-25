package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.entity.ReservationTime;

import javax.sql.DataSource;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class ReservationTimeRepositoryTest {

    private final JdbcTemplate jdbcTemplate;
    private final ReservationTimeRepository timeRepository;

    @Autowired
    ReservationTimeRepositoryTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.timeRepository = new ReservationTimeH2Repository(jdbcTemplate, dataSource);
    }


    @BeforeEach
    void setUp() {
        String sql = "INSERT INTO reservation_time (id, start_at) VALUES (?, ?)";
        List<ReservationTime> times = List.of(
                new ReservationTime(11L, LocalTime.of(10, 0)),
                new ReservationTime(12L, LocalTime.of(11, 0))
        );
        List<Object[]> batchArgs = times.stream().map(time -> new Object[]{
                time.id(),
                time.startAt().format(DateTimeFormatter.ISO_LOCAL_TIME)
        }).toList();
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    @Test
    @DisplayName("모든 예약 시간 목록을 조회한다.")
    void findAll() {
        // given
        List<ReservationTime> times = timeRepository.findAll();

        // when & then
        assertThat(times).containsExactlyInAnyOrder(
                new ReservationTime(11L, LocalTime.of(10, 0)),
                new ReservationTime(12L, LocalTime.of(11, 0))
        );
    }

    @Test
    @DisplayName("예약 시간 정보를 저장하면 새로운 아이디가 부여된다.")
    void save() {
        // given
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.of(12, 0));
        ReservationTime expected = new ReservationTime(1L, LocalTime.of(12, 0));

        // when
        ReservationTime actual = timeRepository.save(reservationTime);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("등록된 예약 시간 번호로 삭제한다.")
    void deleteAssignedId() {
        // given
        Long id = 12L;

        // when & then
        assertThat(timeRepository.findById(id)).isNotNull();
        assertThat(timeRepository.deleteById(id)).isNotZero();
    }

    @Test
    @DisplayName("없는 예약 시간 번호로 삭제할 경우 아무런 영향이 없다.")
    void deleteNotExistId() {
        // given
        Long id = 13L;

        // when & then
        assertThat(timeRepository.findById(id)).isNull();
        assertThat(timeRepository.deleteById(id)).isZero();
    }
}
