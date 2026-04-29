package roomescape.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Time;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TimeRepositoryTest {

    @Autowired
    TimeRepository timeRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Test
    @DisplayName("전체 시간을 조회한다.")
    void findAllTimesTest() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "11:00");

        List<Time> times = timeRepository.findAllTimes();
        assertThat(times).hasSize(2);
    }

    @Test
    @DisplayName("시간을 추가하면 id가 부여된 객체가 반환된다.")
    void addTest() {
        Time saved = timeRepository.add(new Time(null, "10:00"));

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getStartAt()).isEqualTo("10:00");
    }

    @Test
    @DisplayName("시간을 삭제한다.")
    void removeTest() {
        Time saved = timeRepository.add(new Time(null, "10:00"));

        timeRepository.remove(saved.getId());

        List<Time> times = timeRepository.findAllTimes();
        assertThat(times).hasSize(0);
    }

    @AfterEach
    void afterEach() {
        jdbcTemplate.update("DELETE FROM reservation");
        jdbcTemplate.update("DELETE FROM reservation_time");
    }
}
