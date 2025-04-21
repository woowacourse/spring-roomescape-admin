package roomescape.time.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.time.domain.Time;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class H2TimeRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private TimeRepository timeRepository;

    @BeforeEach
    void beforeEach() {
        timeRepository = new H2TimeRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("모든 시간을 조회한다.")
    void findAll() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");

        // when
        List<Time> times = timeRepository.findAll();

        // then
        Time expected = new Time(1L, LocalTime.of(10, 0));
        assertThat(times.size()).isEqualTo(1);
        assertThat(times.getFirst()).isEqualTo(expected);
    }

    @Test
    @DisplayName("id로 시간을 조회한다.")
    void findById() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");

        // when
        Optional<Time> time = timeRepository.findById(1L);

        // then
        Time expected = new Time(1L, LocalTime.of(10, 0));
        assertThat(time.get()).isEqualTo(expected);
    }

    @Test
    @DisplayName("시간을 저장한다.")
    void save() {
        // given
        Time time = new Time(
                null,
                LocalTime.of(10, 0)
        );

        // when
        Time saved = timeRepository.save(time);

        // then
        assertThat(saved.getId()).isEqualTo(1L);
        assertThat(saved.getStartAt()).isEqualTo(time.getStartAt());
    }

    @Test
    @DisplayName("id로 시간을 삭제한다.")
    void deleteById() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");

        // when
        timeRepository.deleteById(1L);

        // then
        List<Time> times = jdbcTemplate.query(
                "select id, start_at from reservation_time",
                (resultSet, rowNum) -> new Time(
                        resultSet.getLong("id"),
                        LocalTime.parse(resultSet.getString("start_at"))
                )
        );
        assertThat(times.size()).isEqualTo(0);
    }
}
