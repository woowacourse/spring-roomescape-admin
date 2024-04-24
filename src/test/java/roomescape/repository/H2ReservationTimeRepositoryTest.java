package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.ReservationTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class H2ReservationTimeRepositoryTest {
    private final JdbcTemplate jdbcTemplate;
    private final H2ReservationTimeRepository repository;

    @Autowired
    public H2ReservationTimeRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.repository = new H2ReservationTimeRepository(jdbcTemplate);
    }

    @BeforeEach
    void setup() {
        jdbcTemplate.update("TRUNCATE TABLE reservation_time");
        jdbcTemplate.update("INSERT INTO reservation_time VALUES (0, '11:00')");
    }

    @DisplayName("모든 시간 조회 테스트")
    @Test
    void findAllTest() {
        assertThat(repository.findAll())
                .hasSize(1);
    }

    @DisplayName("id를 통해 시간 조회 테스트")
    @Test
    void findByIdTest() {
        assertThat(repository.findById(0L))
                .isEqualTo(new ReservationTime(0L, "11:00"));
    }
}
