package roomescape.data.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.business.domain.Time;
import roomescape.data.entity.TimeEntity;

@JdbcTest
class JdbcTimeDaoTest {

    private TimeDao timeDao;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    JdbcTimeDaoTest(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeEach
    void setUp() {
        timeDao = new JdbcTimeDao(jdbcTemplate);
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation_time CASCADE");
        jdbcTemplate.execute("""
                CREATE TABLE reservation_time
                (
                    id SERIAL,
                    start_at VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                );
                """);

    }

    @DisplayName("데이터베이스에 방탈출 시간을 저장한다.")
    @Test
    void save() {
        // given & when
        final Long id = timeDao.save(new Time(LocalTime.of(10, 10)));
        final TimeEntity actual = jdbcTemplate.queryForObject(
                "SELECT id, start_at FROM reservation_time WHERE id = ?",
                TimeEntity.getDefaultRowMapper(), id
        );

        // then
        assertThat(actual).isEqualTo(new TimeEntity(1L, "10:10"));
    }

    @DisplayName("데이터베이스에서 방탈출 시간을 찾는다.")
    @Test
    void find() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES ('10:10')");

        // when
        final Optional<Time> actual = timeDao.find(1L);

        // then
        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo(new Time(1L, LocalTime.of(10, 10)));
    }

    @DisplayName("데이터베이스에서 모든 방탈출 시간을 찾는다.")
    @Test
    void findAll() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES ('10:10')");
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES ('11:10')");

        // when
        final List<Time> actual = timeDao.findAll();

        // then
        assertThat(actual).containsExactly(
                new Time(1L, LocalTime.of(10, 10)),
                new Time(2L, LocalTime.of(11, 10))
        );
    }
}
