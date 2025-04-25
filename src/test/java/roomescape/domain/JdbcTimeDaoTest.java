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
import roomescape.reservation.domain.repository.JdbcTimeDao;
import roomescape.util.TestDataSourceFactory;

class JdbcTimeDaoTest {

    private JdbcTimeDao jdbcTimeDao;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        DataSource dataSource = TestDataSourceFactory.getEmbeddedDataSource();
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTimeDao = new JdbcTimeDao(jdbcTemplate);
    }

    @DisplayName("시간 데이터를 저장한다")
    @Test
    void save_time_test() {
        // given
        LocalTime startAt = LocalTime.of(15, 21);
        Time time = new Time(null, startAt);

        // when
        Long id = jdbcTimeDao.saveAndReturnId(time);

        // then
        assertThat(id).isEqualTo(7L);
    }

    @DisplayName("저장된 시간 데이터를 모두 조회한다")
    @Test
    void get_all_test() {
        // when
        List<Time> all = jdbcTimeDao.findAll();

        // then
        assertThat(all).hasSize(6);
    }

    @DisplayName("저장된 시간 데이터를 삭제한다")
    @Test
    void delete_time_test() {
        // given
        Long id = 6L;

        // when
        jdbcTimeDao.deleteById(id);

        // then
        String sql = "SELECT COUNT(*) FROM reservation_time";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        assertThat(count).isEqualTo(5);
    }

    @DisplayName("id 값에 해당하는 Time 객체를 조회한다")
    @Test
    void find_by_id_test() {
        // given
        Long id = 1L;

        // when
        Time findTime = jdbcTimeDao.findById(id);

        // then
        assertThat(findTime.getId()).isEqualTo(1L);
        assertThat(findTime.getStartAt()).isEqualTo(LocalTime.of(10, 0));
    }
}
