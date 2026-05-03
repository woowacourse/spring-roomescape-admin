package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.ReservationTime;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(TimeUpdatingRepository.class)
public class TimeUpdatingRepositoryTest {

    @Autowired
    private TimeUpdatingRepository timeUpdatingRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation_time");
        jdbcTemplate.execute("""
                CREATE TABLE reservation_time (
                    id       BIGINT       NOT NULL AUTO_INCREMENT,
                    start_at VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                )""");
    }

    @Test
    @DisplayName("새로운 예약 시간을 추가하면 생성된 ID를 반환한다")
    void insert() {
        ReservationTime time = new ReservationTime(null, LocalTime.of(10, 0));

        Long generatedId = timeUpdatingRepository.insert(time);

        assertThat(generatedId).isNotNull();
        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM reservation_time WHERE id = ?", Integer.class, generatedId);
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("예약 시간을 삭제하면 해당 데이터가 DB에서 제거된다")
    void delete() {
        jdbcTemplate.execute("INSERT INTO reservation_time (start_at) VALUES ('10:00:00')");
        Long id = jdbcTemplate.queryForObject("SELECT id FROM reservation_time LIMIT 1", Long.class);

        timeUpdatingRepository.delete(id);

        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM reservation_time WHERE id = ?", Integer.class, id);
        assertThat(count).isEqualTo(0);
    }
}