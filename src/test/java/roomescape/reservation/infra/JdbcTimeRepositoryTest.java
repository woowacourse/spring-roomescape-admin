package roomescape.reservation.infra;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.domain.Time;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JdbcTimeRepositoryTest {
    @Autowired
    private JdbcTimeRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 시간_저장_레포지토리_테스트() {
        Time savedTime = repository.save(LocalTime.of(15, 40));
        Long id = jdbcTemplate.queryForObject("SELECT id FROM reservation_time LIMIT 1", Long.class);

        assertThat(savedTime.getId()).isEqualTo(id);
        assertThat(savedTime.getStartAt()).isEqualTo(LocalTime.of(15, 40));
    }
}
