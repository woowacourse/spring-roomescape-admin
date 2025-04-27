package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Time;

@JdbcTest
class TimeDAOImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("모든 time 을 조회한다")
    void findAllTime() {
        // given
        TimeDAOImpl timeDAOImpl = new TimeDAOImpl(jdbcTemplate);

        // when
        List<Time> times = timeDAOImpl.findAllTime();

        // then
        assertThat(times).hasSize(1);
    }

    @Test
    @DisplayName("reservation 을 추가한다")
    void insertTime() {
        // given
        TimeDAOImpl timeDAOImpl = new TimeDAOImpl(jdbcTemplate);
        Time time = new Time("10:00");

        // when
        Long id = timeDAOImpl.insertTime(time);

        // then
        assertThat(id).isNotEqualTo(-1);
    }

    @Test
    @DisplayName("reservation 을 삭제한다")
    void deleteTimeById() {
        // given
        TimeDAOImpl timeDAOImpl = new TimeDAOImpl(jdbcTemplate);
        Time time = new Time("10:00");
        Long id = timeDAOImpl.insertTime(time);

        // when
        int deletedCount = timeDAOImpl.deleteTimeById(id);

        // then
        assertThat(deletedCount).isEqualTo(1);
    }
}
