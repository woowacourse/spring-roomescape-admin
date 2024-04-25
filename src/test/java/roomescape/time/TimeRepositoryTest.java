package roomescape.time;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.time.domain.Time;
import roomescape.time.repository.TimeRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TimeRepositoryTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void beforeEach() {
        RestAssured.port = port;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TimeRepository timeRepository;

    @Test
    @DisplayName("데이터를 저장한다")
    void saveTest() {
        Time time = new Time(null, LocalTime.now());
        Long saveId = timeRepository.save(time);

        assertThat(saveId).isEqualTo(1);
    }

    @Test
    @DisplayName("id 로 엔티티를 찾는다.")
    void findByIdTest() {
        Time time1 = new Time(1L, LocalTime.now());
        Time time2 = new Time(2L, LocalTime.now());
        timeRepository.save(time1);
        timeRepository.save(time2);
        Time findTime = timeRepository.findById(time2.getId());
        assertThat(findTime.getId()).isEqualTo(2);
    }

    @Test
    @DisplayName("전체 엔티티를 조회한다.")
    void findAllTest() {
        Time time1 = new Time(1L, LocalTime.now());
        Time time2 = new Time(2L, LocalTime.now());
        timeRepository.save(time1);
        timeRepository.save(time2);
        List<Time> times = timeRepository.findAll();
        assertThat(times.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("id를 받아 삭제한다.")
    void deleteTest() {
        Time time = new Time(1L, LocalTime.now());
        timeRepository.save(time);
        timeRepository.delete(1L);
        List<Time> times = timeRepository.findAll();

        assertThat(times.size()).isEqualTo(0);
    }
}
