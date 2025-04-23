package roomescape.domain;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.reservation.domain.Time;
import roomescape.reservation.domain.repository.TimeDao;
import roomescape.util.H2DataSourceFactory;

class TimeDaoTest {

    private TimeDao timeDao;

    @BeforeEach
    void setup() {
        DataSource dataSource = H2DataSourceFactory.getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        timeDao = new TimeDao(jdbcTemplate);

        H2DataSourceFactory.initializeTable(jdbcTemplate.getDataSource());

        List<Time> times = List.of(
                new Time(LocalTime.of(03, 21)),
                new Time(LocalTime.of(15, 32)),
                new Time(LocalTime.of(22, 04))
        );

        String sql = "insert into reservation_time (start_at) values (?)";

        List<Object[]> startTimes = times.stream()
                .map(time -> new Object[]{time.getStartAt().toString()})
                .toList();

        jdbcTemplate.batchUpdate(sql, startTimes);

        System.out.println(timeDao.getCount());
    }

    @DisplayName("시간 데이터를 저장한다")
    @Test
    void save_time_test() {
        // given
        LocalTime startAt = LocalTime.of(15, 21);
        Time time = new Time(startAt);

        // when
        Time saveTime = timeDao.saveTime(time);

        // then
        LocalTime expected = LocalTime.of(15, 21);
        assertThat(saveTime.getStartAt()).isEqualTo(expected);
    }

    @DisplayName("저장된 시간 데이터를 모두 조회한다")
    @Test
    void get_all_test() {
        // when
        List<Time> all = timeDao.getAll();

        // then
        assertThat(all).hasSize(3);
    }

    @DisplayName("저장된 시간 데이터를 삭제한다")
    @Test
    void delete_time_test() {
        // given
        Long id = 1L;

        // when
        timeDao.deleteById(id);

        // then
        assertThat(timeDao.getAll()).hasSize(2);
    }

    @DisplayName("id 값에 해당하는 Time 객체를 조회한다")
    @Test
    void find_by_id_test() {
        // given
        Long id = 1L;

        // when
        Time findTime = timeDao.findById(id);

        // then
        assertThat(findTime.getId()).isEqualTo(1L);
        assertThat(findTime.getStartAt()).isEqualTo(LocalTime.of(03, 21));
    }
}
