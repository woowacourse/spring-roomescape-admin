package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import roomescape.domain.Time;
import roomescape.fixture.TimeFixture;

@JdbcTest
class TimeRepositoryTest {

    @Autowired
    private DataSource dataSource;

    private TimeRepository timeRepository;

    @BeforeEach
    void setUp() {
        timeRepository = new TimeRepository(dataSource);
    }

    @Test
    @DisplayName("예약 시간을 추가한다.")
    void save() {
        // given
        Time time = TimeFixture.entity(0);

        // when
        Time saved = timeRepository.save(time);

        // then
        Time found = timeRepository.findById(saved.getId()).get();
        assertThat(saved).isEqualTo(found);
    }

    @Test
    @DisplayName("예약 시간을 조회한다.")
    void findById() {
        // given
        Time time1 = TimeFixture.entity(0);
        Time time2 = TimeFixture.entity(1);
        Time saved = timeRepository.save(time1);
        timeRepository.save(time1);

        // when
        Time found = timeRepository.findById(saved.getId()).get();

        // then
        assertThat(found.getStartAt().truncatedTo(ChronoUnit.MINUTES))
                .isEqualTo(saved.getStartAt().truncatedTo(ChronoUnit.MINUTES));
    }

    @Test
    @DisplayName("모든 예약 시간을 조회한다.")
    void findAll() {
        // given
        Time time1 = TimeFixture.entity(0);
        Time time2 = TimeFixture.entity(1);
        Time time3 = TimeFixture.entity(2);
        Time saved1 = timeRepository.save(time1);
        Time saved2 = timeRepository.save(time2);
        Time saved3 = timeRepository.save(time3);

        // when
        List<Time> result = timeRepository.findAll();

        // then
        assertThat(result).containsExactly(saved1, saved2, saved3);
    }

    @Test
    @DisplayName("예약 시간을 삭제한다.")
    void delete() {
        // given
        Time time = TimeFixture.entity(0);
        Time saved = timeRepository.save(time);
        assertThat(timeRepository.findById(saved.getId())).isNotEmpty();

        // when
        timeRepository.delete(saved.getId());

        // then
        assertThat(timeRepository.findById(saved.getId())).isEmpty();
    }
}
