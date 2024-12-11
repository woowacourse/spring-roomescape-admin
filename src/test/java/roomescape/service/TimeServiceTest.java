package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import roomescape.fixture.TimeFixture;
import roomescape.service.dto.TimeRequest;
import roomescape.service.dto.TimeResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class TimeServiceTest {

    @Autowired
    private TimeService timeService;

    @Test
    void create() {
        // given
        TimeRequest timeRequest = TimeFixture.request(1);

        // when
        TimeResponse result = timeService.create(timeRequest);

        // then
        TimeResponse expected = TimeFixture.response(result.id(), 1);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void findOne() {
        // given
        TimeRequest timeRequest = TimeFixture.request(1);
        TimeResponse expected = timeService.create(timeRequest);
        long id = expected.id();

        // when
        TimeResponse result = timeService.findOne(id);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void findAll() {
        // given
        TimeRequest request1 = TimeFixture.request(1);
        TimeRequest request2 = TimeFixture.request(1);
        TimeRequest request3 = TimeFixture.request(1);
        TimeResponse response1 = timeService.create(request1);
        TimeResponse response2 = timeService.create(request2);
        TimeResponse response3 = timeService.create(request3);

        // when
        List<TimeResponse> result = timeService.findAll();

        // then
        assertThat(result).containsExactly(response1, response2, response3);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void remove() {
        // given
        TimeResponse savedResponse = timeService.create(TimeFixture.request(1));

        // when
        timeService.remove(savedResponse.id());

        // then
        assertThatThrownBy(() -> timeService.findOne(savedResponse.id()))
                .isInstanceOf(NoSuchElementException.class);
    }
}
