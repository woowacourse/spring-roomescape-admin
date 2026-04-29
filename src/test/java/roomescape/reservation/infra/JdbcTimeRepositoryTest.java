package roomescape.reservation.infra;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
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

    @Test
    void 전체_시간_조회_레포지토리_테스트() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "15:00");
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "16:00");

        List<Time> times = repository.findAll();

        assertThat(times).hasSize(2);
        assertThat(times)
                .extracting(Time::getStartAt)
                .containsExactly(LocalTime.of(15, 00), LocalTime.of(16, 00));
    }
}
