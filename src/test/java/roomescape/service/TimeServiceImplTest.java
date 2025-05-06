package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dao.TestTimeDAOImpl;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;

class TimeServiceImplTest {

    @Test
    @DisplayName("db의 모든 Time 목록을 조회한다")
    void findAllTime() {
        // given
        TimeServiceImpl timeService = new TimeServiceImpl(new TestTimeDAOImpl());
        timeService.createTime(new TimeRequest("10:00"));

        // when
        List<TimeResponse> times = timeService.findAllTime();

        // then
        assertThat(times).hasSize(1);
    }

    @Test
    @DisplayName("db의 Time 을 추가한다")
    void createTime() {
        // given
        TimeServiceImpl timeService = new TimeServiceImpl(new TestTimeDAOImpl());
        String startAt = "10:00";
        TimeRequest timeRequest = new TimeRequest(startAt);

        // when
        TimeResponse timeResponse = timeService.createTime(timeRequest);

        // then
        assertThat(timeResponse.startAt()).isEqualTo(startAt);
    }

    @Test
    @DisplayName("db의 Time 을 삭제한다")
    void deleteTimeById() {
        // given
        TimeServiceImpl timeService = new TimeServiceImpl(new TestTimeDAOImpl());
        TimeRequest timeRequest = new TimeRequest("10:00");
        TimeResponse timeResponse = timeService.createTime(timeRequest);
        Long id = timeResponse.id();

        // when
        int count = timeService.deleteTimeById(id);

        // then
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("db의 Time 이 존재하는지 확인한다")
    void existsById() {
        // given
        TimeServiceImpl timeService = new TimeServiceImpl(new TestTimeDAOImpl());
        TimeRequest timeRequest = new TimeRequest("10:00");
        TimeResponse timeResponse = timeService.createTime(timeRequest);
        Long id = timeResponse.id();

        // when
        boolean existsId = timeService.existsById(id);
        boolean notExistsId = timeService.existsById(100L);

        // then
        assertAll(
                () -> assertThat(existsId).isTrue(),
                () -> assertThat(notExistsId).isFalse()
        );
    }
}
