package roomescape.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.reservation.model.Time;
import roomescape.reservation.model.TimeDetails;

@JdbcTest(properties = "application-test.properties")
@Import(TimeRepository.class)
class TimeRepositoryTest {

    @Autowired
    TimeRepository timeRepository;

    TimeDetails timeDetails = new TimeDetails(LocalTime.of(12, 0));

    @DisplayName("Time을 추가한다.")
    @Test
    void insertTime() {
        // when
        Time time = timeRepository.insertTime(timeDetails);

        // then
        assertThat(time)
                .hasFieldOrPropertyWithValue("startAt", LocalTime.of(12, 0));
    }

    @DisplayName("id에 해당하는 Time을 삭제한다.")
    @Test
    void deleteById() {
        // given
        Time time = timeRepository.insertTime(timeDetails);

        // when
        boolean isDeleted = timeRepository.deleteTimeById(time.getId());

        // then
        assertThat(isDeleted).isTrue();
    }

    @DisplayName("전체 Time을 읽어온다.")
    @Test
    void findAll() {
        // given
        timeRepository.insertTime(timeDetails);

        // when
        List<Time> times = timeRepository.findAll();

        // then
        assertThat(times).hasSize(1);
        assertThat(times.getFirst())
                .hasFieldOrPropertyWithValue("startAt", LocalTime.of(12, 0));
    }
}